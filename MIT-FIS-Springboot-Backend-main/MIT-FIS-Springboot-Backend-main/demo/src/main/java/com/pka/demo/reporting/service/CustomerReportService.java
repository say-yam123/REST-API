package com.pka.demo.reporting.service;

import com.pka.demo.reporting.ReportType;
import com.pka.demo.reporting.dto.ReportDefinitionResponse;
import com.pka.demo.reporting.dto.ReportRequest;
import com.pka.demo.reporting.dto.ReportRunResponse;
import com.pka.demo.reporting.model.ReportArtifact;
import com.pka.demo.reporting.model.ReportQueryResult;
import com.pka.demo.reporting.model.ReportValidationSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class CustomerReportService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerReportService.class);

    private final Path baseOutputDir;
    private final ReportQueryService reportQueryService;
    private final ReportValidationService reportValidationService;
    private final ReportExportService reportExportService;

    public CustomerReportService(
            @Value("${reports.output-dir}") String outputDir,
            ReportQueryService reportQueryService,
            ReportValidationService reportValidationService,
            ReportExportService reportExportService) {
        this.baseOutputDir = Path.of(outputDir).toAbsolutePath().normalize();
        this.reportQueryService = reportQueryService;
        this.reportValidationService = reportValidationService;
        this.reportExportService = reportExportService;
    }

    public List<ReportDefinitionResponse> listDefinitions() {
        return List.of(ReportType.values()).stream()
                .map(type -> new ReportDefinitionResponse(type.getSlug(), type.getDescription()))
                .toList();
    }

    public ReportRunResponse runReport(String reportSlug, ReportRequest request) {
        ReportType reportType = ReportType.fromSlug(reportSlug);
        Instant generatedAt = Instant.now();
        String runId = UUID.randomUUID().toString().substring(0, 8);
        Map<String, Object> parameters = buildParameters(request);
        long startedAt = System.currentTimeMillis();

        logger.info("Running reportName={} runId={} parameters={}", reportType.getSlug(), runId, parameters);
        ReportQueryResult queryResult = reportQueryService.run(reportType.getSqlResource(), parameters);
        List<LinkedHashMap<String, Object>> rows = queryResult.getRows();
        ReportValidationSummary validationSummary = reportValidationService.validate(rows);
        long durationMs = System.currentTimeMillis() - startedAt;
        ReportArtifact artifact = reportExportService.export(
                reportType.getSlug(),
                request.getFormat(),
                runId,
                generatedAt,
                durationMs,
                parameters,
                queryResult.getSqlText(),
                rows,
                validationSummary);

        logger.info("Completed reportName={} runId={} rowCount={} durationMs={} output={} checksum={}",
                reportType.getSlug(),
                runId,
                validationSummary.getRowCount(),
                durationMs,
                artifact.getOutputPath(),
                artifact.getOutputChecksumSha256());

        return new ReportRunResponse(
                reportType.getSlug(),
                request.getFormat(),
                runId,
                generatedAt,
                validationSummary.getRowCount(),
                artifact.getOutputPath().toString(),
                artifact.getMetadataPath().toString(),
                artifact.getSqlSnapshotPath().toString(),
                buildArtifactUrl(reportType.getSlug(), runId, "output"),
                buildArtifactUrl(reportType.getSlug(), runId, "metadata"),
                buildArtifactUrl(reportType.getSlug(), runId, "sql"),
                artifact.getOutputSizeBytes(),
                artifact.getOutputChecksumSha256(),
                durationMs,
                validationSummary.getColumns(),
                validationSummary.getNullCounts(),
                validationSummary.getDuplicateRowCount(),
                validationSummary.getWarnings());
    }

    public Resource loadArtifact(String reportSlug, String runId, String artifactType) {
        ReportType reportType = ReportType.fromSlug(reportSlug);
        Path reportDir = baseOutputDir.resolve(reportType.getSlug()).normalize();
        if (!List.of("output", "metadata", "sql").contains(artifactType.toLowerCase())) {
            throw new IllegalArgumentException("Unsupported artifact type: " + artifactType);
        }

        try (Stream<Path> paths = Files.list(reportDir)) {
            Path artifactPath = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().contains("__" + runId))
                    .filter(path -> matchesArtifactType(path, artifactType))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No " + artifactType + " artifact found for report " + reportType.getSlug() + " and runId " + runId));
            return new FileSystemResource(artifactPath);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read report artifacts for " + reportType.getSlug(), exception);
        }
    }

    private boolean matchesArtifactType(Path path, String artifactType) {
        String fileName = path.getFileName().toString().toLowerCase();
        return switch (artifactType.toLowerCase()) {
            case "output" -> !fileName.endsWith(".json") && !fileName.endsWith(".sql");
            case "metadata" -> fileName.endsWith(".json");
            case "sql" -> fileName.endsWith(".sql");
            default -> false;
        };
    }

    private String buildArtifactUrl(String reportSlug, String runId, String artifactType) {
        return "/api/reports/" + reportSlug + "/runs/" + runId + "/artifacts/" + artifactType;
    }

    private Map<String, Object> buildParameters(ReportRequest request) {
        LocalDate today = LocalDate.now();
        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("startDate", request.getStartDate());
        parameters.put("endDate", request.getEndDate());
        parameters.put("asOfDate", request.getAsOfDate() == null ? today : request.getAsOfDate());
        parameters.put("status", blankToNull(request.getStatus()));
        parameters.put("country", blankToNull(request.getCountry()));
        parameters.put("contactType", blankToNull(request.getContactType()));
        parameters.put("proofType", blankToNull(request.getProofType()));
        return parameters;
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
