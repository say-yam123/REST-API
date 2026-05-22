package com.pka.demo.reporting.dto;

import com.pka.demo.reporting.ReportFormat;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class ReportRunResponse {
    private final String reportName;
    private final ReportFormat format;
    private final String runId;
    private final Instant generatedAt;
    private final long rowCount;
    private final String outputFile;
    private final String metadataFile;
    private final String sqlSnapshotFile;
    private final String downloadUrl;
    private final String metadataDownloadUrl;
    private final String sqlSnapshotDownloadUrl;
    private final long outputSizeBytes;
    private final String outputChecksumSha256;
    private final long durationMs;
    private final List<String> columns;
    private final Map<String, Long> nullCounts;
    private final long duplicateRowCount;
    private final List<String> warnings;

    public ReportRunResponse(
            String reportName,
            ReportFormat format,
            String runId,
            Instant generatedAt,
            long rowCount,
            String outputFile,
            String metadataFile,
            String sqlSnapshotFile,
            String downloadUrl,
            String metadataDownloadUrl,
            String sqlSnapshotDownloadUrl,
            long outputSizeBytes,
            String outputChecksumSha256,
            long durationMs,
            List<String> columns,
            Map<String, Long> nullCounts,
            long duplicateRowCount,
            List<String> warnings) {
        this.reportName = reportName;
        this.format = format;
        this.runId = runId;
        this.generatedAt = generatedAt;
        this.rowCount = rowCount;
        this.outputFile = outputFile;
        this.metadataFile = metadataFile;
        this.sqlSnapshotFile = sqlSnapshotFile;
        this.downloadUrl = downloadUrl;
        this.metadataDownloadUrl = metadataDownloadUrl;
        this.sqlSnapshotDownloadUrl = sqlSnapshotDownloadUrl;
        this.outputSizeBytes = outputSizeBytes;
        this.outputChecksumSha256 = outputChecksumSha256;
        this.durationMs = durationMs;
        this.columns = columns;
        this.nullCounts = nullCounts;
        this.duplicateRowCount = duplicateRowCount;
        this.warnings = warnings;
    }

    public String getReportName() {
        return reportName;
    }

    public ReportFormat getFormat() {
        return format;
    }

    public String getRunId() {
        return runId;
    }

    public Instant getGeneratedAt() {
        return generatedAt;
    }

    public long getRowCount() {
        return rowCount;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public String getMetadataFile() {
        return metadataFile;
    }

    public String getSqlSnapshotFile() {
        return sqlSnapshotFile;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getMetadataDownloadUrl() {
        return metadataDownloadUrl;
    }

    public String getSqlSnapshotDownloadUrl() {
        return sqlSnapshotDownloadUrl;
    }

    public long getOutputSizeBytes() {
        return outputSizeBytes;
    }

    public String getOutputChecksumSha256() {
        return outputChecksumSha256;
    }

    public long getDurationMs() {
        return durationMs;
    }

    public List<String> getColumns() {
        return columns;
    }

    public Map<String, Long> getNullCounts() {
        return nullCounts;
    }

    public long getDuplicateRowCount() {
        return duplicateRowCount;
    }

    public List<String> getWarnings() {
        return warnings;
    }
}
