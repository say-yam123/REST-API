package com.pka.demo.reporting.service;

import com.pka.demo.reporting.model.ReportValidationSummary;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportValidationServiceTest {
    private final ReportValidationService reportValidationService = new ReportValidationService();

    @Test
    void shouldSummarizeRowsNullsAndDuplicates() {
        LinkedHashMap<String, Object> first = new LinkedHashMap<>();
        first.put("customer_id", 1L);
        first.put("status", "ACTIVE");

        LinkedHashMap<String, Object> second = new LinkedHashMap<>();
        second.put("customer_id", 1L);
        second.put("status", "ACTIVE");

        LinkedHashMap<String, Object> third = new LinkedHashMap<>();
        third.put("customer_id", 2L);
        third.put("status", null);

        ReportValidationSummary summary = reportValidationService.validate(List.of(first, second, third));

        assertEquals(3, summary.getRowCount());
        assertEquals(1, summary.getDuplicateRowCount());
        assertEquals(1L, summary.getNullCounts().get("status"));
        assertTrue(summary.getWarnings().stream().anyMatch(warning -> warning.contains("duplicate")));
    }
}
