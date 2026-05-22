package com.pka.demo.reporting.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pka.demo.reporting.ReportFormat;
import com.pka.demo.reporting.model.ReportArtifact;
import com.pka.demo.reporting.model.ReportValidationSummary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportExportServiceTest {
    @TempDir
    Path tempDir;

    @Test
    void shouldWriteCsvAndMetadata() throws Exception {
        ReportExportService reportExportService = new ReportExportService(tempDir.toString(), new ObjectMapper());

        LinkedHashMap<String, Object> row = new LinkedHashMap<>();
        row.put("customer_id", 10L);
        row.put("customer_name", "Asha");

        ReportValidationSummary summary = new ReportValidationSummary(
                1,
                List.of("customer_id", "customer_name"),
                Map.of("customer_id", 0L, "customer_name", 0L),
                0,
                List.of());

        ReportArtifact artifact = reportExportService.export(
                "customer-master",
                ReportFormat.CSV,
                "abc12345",
                Instant.parse("2026-04-16T08:30:00Z"),
                142L,
                Map.of("status", "ACTIVE"),
                "select customer_id, customer_name from customers where status = :status",
                List.of(row),
                summary);

        assertTrue(Files.exists(artifact.getOutputPath()));
        assertTrue(Files.exists(artifact.getMetadataPath()));
        assertTrue(Files.exists(artifact.getSqlSnapshotPath()));
        assertTrue(Files.readString(artifact.getOutputPath()).contains("customer_name"));
        assertTrue(Files.readString(artifact.getSqlSnapshotPath()).contains("from customers"));
        assertTrue(Files.readString(artifact.getMetadataPath()).contains("\"outputChecksumSha256\""));
        assertTrue(Files.readString(artifact.getMetadataPath()).contains("\"previewRows\""));
        assertEquals(Files.size(artifact.getOutputPath()), artifact.getOutputSizeBytes());
    }

    @Test
    void shouldWritePdfAndMetadata() throws Exception {
        ReportExportService reportExportService = new ReportExportService(tempDir.toString(), new ObjectMapper());

        LinkedHashMap<String, Object> row = new LinkedHashMap<>();
        row.put("customer_id", 20L);
        row.put("customer_name", "Ravi");

        ReportValidationSummary summary = new ReportValidationSummary(
                1,
                List.of("customer_id", "customer_name"),
                Map.of("customer_id", 0L, "customer_name", 0L),
                0,
                List.of());

        ReportArtifact artifact = reportExportService.export(
                "customer-master",
                ReportFormat.PDF,
                "pdf12345",
                Instant.parse("2026-04-16T08:30:00Z"),
                150L,
                Map.of("status", "ACTIVE"),
                "select customer_id, customer_name from customers where status = :status",
                List.of(row),
                summary);

        assertTrue(Files.exists(artifact.getOutputPath()));
        assertTrue(Files.exists(artifact.getMetadataPath()));
        assertTrue(Files.exists(artifact.getSqlSnapshotPath()));
        assertTrue(Files.size(artifact.getOutputPath()) > 0);
        assertEquals(".pdf", artifact.getOutputPath().getFileName().toString()
                .substring(artifact.getOutputPath().getFileName().toString().lastIndexOf('.')));
    }
}
