package com.pka.demo.reporting.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.pka.demo.reporting.ReportFormat;
import com.pka.demo.reporting.model.ReportArtifact;
import com.pka.demo.reporting.model.ReportValidationSummary;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@Service
public class ReportExportService {
    private static final DateTimeFormatter FILE_TIMESTAMP = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
            .withZone(ZoneId.systemDefault());

    private final Path baseOutputDir;
    private final ObjectMapper objectMapper;

    public ReportExportService(@Value("${reports.output-dir}") String outputDir, ObjectMapper objectMapper) {
        this.baseOutputDir = Path.of(outputDir).toAbsolutePath().normalize();
        this.objectMapper = objectMapper;
    }

    public ReportArtifact export(
            String reportName,
            ReportFormat format,
            String runId,
            Instant generatedAt,
            long durationMs,
            Map<String, Object> parameters,
            String sqlText,
            List<LinkedHashMap<String, Object>> rows,
            ReportValidationSummary validationSummary) {
        try {
            Files.createDirectories(baseOutputDir.resolve(reportName));
            String fileBaseName = reportName + "__" + FILE_TIMESTAMP.format(generatedAt) + "__" + runId;
            Path reportDir = baseOutputDir.resolve(reportName);
            Path outputPath = reportDir.resolve(fileBaseName + format.getExtension());
            Path metadataPath = reportDir.resolve(fileBaseName + ".json");
            Path sqlSnapshotPath = reportDir.resolve(fileBaseName + ".sql");

            switch (format) {
                case CSV -> writeCsv(outputPath, validationSummary.getColumns(), rows);
                case EXCEL -> writeExcel(outputPath, reportName, generatedAt, parameters, validationSummary, rows);
                case HTML -> writeHtml(outputPath, reportName, generatedAt, parameters, validationSummary, rows);
                case PDF -> writePdf(outputPath, reportName, generatedAt, parameters, validationSummary, rows);
            }
            Files.writeString(sqlSnapshotPath, sqlText, StandardCharsets.UTF_8);

            long outputSizeBytes = Files.size(outputPath);
            String outputChecksumSha256 = sha256(outputPath);

            Map<String, Object> metadata = new LinkedHashMap<>();
            metadata.put("reportName", reportName);
            metadata.put("format", format.name());
            metadata.put("runId", runId);
            metadata.put("generatedAt", generatedAt.toString());
            metadata.put("durationMs", durationMs);
            metadata.put("parameters", parameters);
            metadata.put("rowCount", validationSummary.getRowCount());
            metadata.put("columns", validationSummary.getColumns());
            metadata.put("nullCounts", validationSummary.getNullCounts());
            metadata.put("duplicateRowCount", validationSummary.getDuplicateRowCount());
            metadata.put("warnings", validationSummary.getWarnings());
            metadata.put("outputFile", outputPath.toString());
            metadata.put("outputSizeBytes", outputSizeBytes);
            metadata.put("outputChecksumSha256", outputChecksumSha256);
            metadata.put("sqlSnapshotFile", sqlSnapshotPath.toString());
            metadata.put("previewRows", rows.stream().limit(20).toList());

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(metadataPath.toFile(), metadata);

            return new ReportArtifact(outputPath, metadataPath, sqlSnapshotPath, outputSizeBytes, outputChecksumSha256);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to export report " + reportName, exception);
        }
    }

    private void writeCsv(Path outputPath, List<String> columns, List<LinkedHashMap<String, Object>> rows) throws IOException {
        StringJoiner joiner = new StringJoiner(System.lineSeparator());
        joiner.add(String.join(",", columns));
        for (LinkedHashMap<String, Object> row : rows) {
            StringJoiner rowJoiner = new StringJoiner(",");
            for (String column : columns) {
                rowJoiner.add(escapeCsv(row.get(column)));
            }
            joiner.add(rowJoiner.toString());
        }
        Files.writeString(outputPath, joiner.toString(), StandardCharsets.UTF_8);
    }

    private void writeExcel(
            Path outputPath,
            String reportName,
            Instant generatedAt,
            Map<String, Object> parameters,
            ReportValidationSummary validationSummary,
            List<LinkedHashMap<String, Object>> rows) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet dataSheet = workbook.createSheet("Data");
            XSSFSheet summarySheet = workbook.createSheet("Summary");

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font boldFont = workbook.createFont();
            boldFont.setBold(true);
            headerStyle.setFont(boldFont);

            Row headerRow = dataSheet.createRow(0);
            List<String> columns = validationSummary.getColumns();
            for (int index = 0; index < columns.size(); index++) {
                Cell cell = headerRow.createCell(index);
                cell.setCellValue(columns.get(index));
                cell.setCellStyle(headerStyle);
            }

            for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
                Row row = dataSheet.createRow(rowIndex + 1);
                LinkedHashMap<String, Object> data = rows.get(rowIndex);
                for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {
                    Object value = data.get(columns.get(columnIndex));
                    row.createCell(columnIndex).setCellValue(value == null ? "" : value.toString());
                }
            }

            writeSummarySheet(summarySheet, headerStyle, reportName, generatedAt, parameters, validationSummary);

            dataSheet.createFreezePane(0, 1);
            summarySheet.createFreezePane(0, 1);
            for (int index = 0; index < columns.size(); index++) {
                dataSheet.autoSizeColumn(index);
            }
            summarySheet.autoSizeColumn(0);
            summarySheet.autoSizeColumn(1);

            try (OutputStream outputStream = Files.newOutputStream(outputPath)) {
                workbook.write(outputStream);
            }
        }
    }

    private void writeSummarySheet(
            XSSFSheet summarySheet,
            CellStyle headerStyle,
            String reportName,
            Instant generatedAt,
            Map<String, Object> parameters,
            ReportValidationSummary validationSummary) {
        String[][] summaryRows = {
                {"report_name", reportName},
                {"generated_at", generatedAt.toString()},
                {"row_count", Long.toString(validationSummary.getRowCount())},
                {"duplicate_rows", Long.toString(validationSummary.getDuplicateRowCount())},
                {"warnings", String.join(" | ", validationSummary.getWarnings())}
        };

        Row header = summarySheet.createRow(0);
        header.createCell(0).setCellValue("Field");
        header.createCell(1).setCellValue("Value");
        header.getCell(0).setCellStyle(headerStyle);
        header.getCell(1).setCellStyle(headerStyle);

        int rowIndex = 1;
        for (String[] summaryRow : summaryRows) {
            Row row = summarySheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(summaryRow[0]);
            row.createCell(1).setCellValue(summaryRow[1]);
        }

        for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
            Row row = summarySheet.createRow(rowIndex++);
            row.createCell(0).setCellValue("param_" + parameter.getKey());
            row.createCell(1).setCellValue(parameter.getValue() == null ? "" : parameter.getValue().toString());
        }
    }

    private void writeHtml(
            Path outputPath,
            String reportName,
            Instant generatedAt,
            Map<String, Object> parameters,
            ReportValidationSummary validationSummary,
            List<LinkedHashMap<String, Object>> rows) throws IOException {
        Files.writeString(outputPath, buildHtmlDocument(reportName, generatedAt, parameters, validationSummary, rows), StandardCharsets.UTF_8);
    }

    private void writePdf(
            Path outputPath,
            String reportName,
            Instant generatedAt,
            Map<String, Object> parameters,
            ReportValidationSummary validationSummary,
            List<LinkedHashMap<String, Object>> rows) throws IOException {
        String html = buildHtmlDocument(reportName, generatedAt, parameters, validationSummary, rows);
        try (OutputStream outputStream = Files.newOutputStream(outputPath)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.toStream(outputStream);
            builder.run();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to render PDF report " + reportName, exception);
        }
    }

    private String buildHtmlDocument(
            String reportName,
            Instant generatedAt,
            Map<String, Object> parameters,
            ReportValidationSummary validationSummary,
            List<LinkedHashMap<String, Object>> rows) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head><meta charset=\"UTF-8\" /><title>")
                .append(escapeHtml(reportName))
                .append("</title><style>")
                .append("@page{size:A4 landscape;margin:18mm;}")
                .append("body{font-family:Segoe UI,Arial,sans-serif;background:#f5f6f8;color:#18202a;padding:24px;}")
                .append(".card{background:#fff;border-radius:16px;padding:20px;box-shadow:0 10px 30px rgba(0,0,0,.08);margin-bottom:18px;}")
                .append("table{width:100%;border-collapse:collapse;font-size:14px;}th,td{border:1px solid #d9dee7;padding:10px;text-align:left;}")
                .append("th{background:#eef3f8;}h1,h2{margin-top:0;}code{background:#eef3f8;padding:2px 6px;border-radius:6px;}")
                .append("tr{page-break-inside:avoid;}thead{display:table-header-group;}tbody{display:table-row-group;}")
                .append("</style></head><body>");

        html.append("<div class=\"card\"><h1>").append(escapeHtml(reportName)).append("</h1>")
                .append("<p>Generated at <code>").append(escapeHtml(generatedAt.toString())).append("</code></p>")
                .append("<p>Rows: <strong>").append(validationSummary.getRowCount()).append("</strong></p>")
                .append("<p>Warnings: ")
                .append(escapeHtml(validationSummary.getWarnings().isEmpty() ? "None" : String.join(", ", validationSummary.getWarnings())))
                .append("</p>")
                .append("</div>");

        html.append("<div class=\"card\"><h2>Parameters</h2><table><tr><th>Name</th><th>Value</th></tr>");
        for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
            html.append("<tr><td>").append(escapeHtml(parameter.getKey())).append("</td><td>")
                    .append(escapeHtml(parameter.getValue() == null ? "" : parameter.getValue().toString()))
                    .append("</td></tr>");
        }
        html.append("</table></div>");

        html.append("<div class=\"card\"><h2>Data</h2><table><tr>");
        for (String column : validationSummary.getColumns()) {
            html.append("<th>").append(escapeHtml(column)).append("</th>");
        }
        html.append("</tr>");
        for (LinkedHashMap<String, Object> row : rows) {
            html.append("<tr>");
            for (String column : validationSummary.getColumns()) {
                html.append("<td>")
                        .append(escapeHtml(row.get(column) == null ? "" : row.get(column).toString()))
                        .append("</td>");
            }
            html.append("</tr>");
        }
        html.append("</table></div></body></html>");
        return html.toString();
    }

    private String escapeCsv(Object value) {
        String stringValue = value == null ? "" : value.toString();
        if (stringValue.contains(",") || stringValue.contains("\"") || stringValue.contains("\n")) {
            return "\"" + stringValue.replace("\"", "\"\"") + "\"";
        }
        return stringValue;
    }

    private String escapeHtml(String value) {
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    private String sha256(Path path) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(Files.readAllBytes(path));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 algorithm is not available", exception);
        }
    }
}
