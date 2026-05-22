package com.pka.demo.reporting.dto;

import com.pka.demo.reporting.ReportFormat;

import java.time.LocalDate;

public class ReportRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate asOfDate;
    private String status;
    private String country;
    private String contactType;
    private String proofType;
    private ReportFormat format = ReportFormat.CSV;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(LocalDate asOfDate) {
        this.asOfDate = asOfDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getProofType() {
        return proofType;
    }

    public void setProofType(String proofType) {
        this.proofType = proofType;
    }

    public ReportFormat getFormat() {
        return format == null ? ReportFormat.CSV : format;
    }

    public void setFormat(ReportFormat format) {
        this.format = format;
    }
}
