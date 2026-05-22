package com.pka.demo.controller;

import com.pka.demo.reporting.dto.ReportDefinitionResponse;
import com.pka.demo.reporting.dto.ReportRequest;
import com.pka.demo.reporting.dto.ReportRunResponse;
import com.pka.demo.reporting.service.CustomerReportService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:4200")
public class ReportController {
    private final CustomerReportService customerReportService;

    public ReportController(CustomerReportService customerReportService) {
        this.customerReportService = customerReportService;
    }

    @GetMapping
    public List<ReportDefinitionResponse> getReports() {
        return customerReportService.listDefinitions();
    }

    @PostMapping("/{reportName}")
    public ResponseEntity<ReportRunResponse> runReport(
            @PathVariable String reportName,
            @RequestBody(required = false) ReportRequest request) {
        ReportRequest safeRequest = request == null ? new ReportRequest() : request;
        return ResponseEntity.ok(customerReportService.runReport(reportName, safeRequest));
    }

    @GetMapping("/{reportName}/runs/{runId}/artifacts/{artifactType}")
    public ResponseEntity<Resource> downloadArtifact(
            @PathVariable String reportName,
            @PathVariable String runId,
            @PathVariable String artifactType) {
        Resource resource = customerReportService.loadArtifact(reportName, runId, artifactType);
        String filename = resource.getFilename() == null ? artifactType : resource.getFilename();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(resolveContentType(filename))
                .body(resource);
    }

    private MediaType resolveContentType(String filename) {
        String lowerCaseFilename = filename.toLowerCase();
        if (lowerCaseFilename.endsWith(".csv")) {
            return MediaType.parseMediaType("text/csv");
        }
        if (lowerCaseFilename.endsWith(".xlsx")) {
            return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        }
        if (lowerCaseFilename.endsWith(".html")) {
            return MediaType.TEXT_HTML;
        }
        if (lowerCaseFilename.endsWith(".pdf")) {
            return MediaType.APPLICATION_PDF;
        }
        if (lowerCaseFilename.endsWith(".json")) {
            return MediaType.APPLICATION_JSON;
        }
        if (lowerCaseFilename.endsWith(".sql")) {
            return MediaType.parseMediaType("application/sql");
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }
}
