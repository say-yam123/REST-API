package com.pka.demo.reporting;

public enum ReportFormat {
    CSV(".csv"),
    EXCEL(".xlsx"),
    HTML(".html"),
    PDF(".pdf");

    private final String extension;

    ReportFormat(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}
