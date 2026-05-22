package com.pka.demo.reporting.model;

import java.util.List;
import java.util.Map;

public class ReportValidationSummary {
    private final long rowCount;
    private final List<String> columns;
    private final Map<String, Long> nullCounts;
    private final long duplicateRowCount;
    private final List<String> warnings;

    public ReportValidationSummary(
            long rowCount,
            List<String> columns,
            Map<String, Long> nullCounts,
            long duplicateRowCount,
            List<String> warnings) {
        this.rowCount = rowCount;
        this.columns = columns;
        this.nullCounts = nullCounts;
        this.duplicateRowCount = duplicateRowCount;
        this.warnings = warnings;
    }

    public long getRowCount() {
        return rowCount;
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
