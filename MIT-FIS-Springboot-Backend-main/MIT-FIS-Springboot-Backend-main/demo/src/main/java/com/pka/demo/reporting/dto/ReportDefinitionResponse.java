package com.pka.demo.reporting.dto;

public class ReportDefinitionResponse {
    private final String name;
    private final String description;

    public ReportDefinitionResponse(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
