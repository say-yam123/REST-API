package com.pka.demo.reporting.model;

import java.nio.file.Path;

public class ReportArtifact {
    private final Path outputPath;
    private final Path metadataPath;
    private final Path sqlSnapshotPath;
    private final long outputSizeBytes;
    private final String outputChecksumSha256;

    public ReportArtifact(
            Path outputPath,
            Path metadataPath,
            Path sqlSnapshotPath,
            long outputSizeBytes,
            String outputChecksumSha256) {
        this.outputPath = outputPath;
        this.metadataPath = metadataPath;
        this.sqlSnapshotPath = sqlSnapshotPath;
        this.outputSizeBytes = outputSizeBytes;
        this.outputChecksumSha256 = outputChecksumSha256;
    }

    public Path getOutputPath() {
        return outputPath;
    }

    public Path getMetadataPath() {
        return metadataPath;
    }

    public Path getSqlSnapshotPath() {
        return sqlSnapshotPath;
    }

    public long getOutputSizeBytes() {
        return outputSizeBytes;
    }

    public String getOutputChecksumSha256() {
        return outputChecksumSha256;
    }
}
