package com.pka.demo.reporting;

import java.util.Arrays;

public enum ReportType {
    CUSTOMER_MASTER("customer-master", "sql/reporting/customer_master.sql", "Customer-level export with latest contact and address"),
    CONTACT_COVERAGE("contact-coverage", "sql/reporting/contact_coverage.sql", "Contact coverage and active contact health by customer"),
    KYC_EXPIRY("kyc-expiry", "sql/reporting/kyc_expiry.sql", "Proof-of-ID expiry and missing KYC exceptions");

    private final String slug;
    private final String sqlResource;
    private final String description;

    ReportType(String slug, String sqlResource, String description) {
        this.slug = slug;
        this.sqlResource = sqlResource;
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public String getSqlResource() {
        return sqlResource;
    }

    public String getDescription() {
        return description;
    }

    public static ReportType fromSlug(String slug) {
        return Arrays.stream(values())
                .filter(value -> value.slug.equalsIgnoreCase(slug))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported report type: " + slug));
    }
}
