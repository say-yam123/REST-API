package com.pka.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CustomerClassificationType")
public class CustomerClassificationTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerClassificationId;

    @Column(nullable = false)
    private String customerClassificationType;
    @Column(nullable = false)
    private String customerClassificationValue;
    @Column(nullable = false)
    private LocalDate effectiveDate;

    public CustomerClassificationTypeEntity() {
    }

    public Long getCustomerClassificationId() {
        return customerClassificationId;
    }

    public void setCustomerClassificationId(Long customerClassificationId) {
        this.customerClassificationId = customerClassificationId;
    }

    public String getCustomerClassificationType() {
        return customerClassificationType;
    }

    public void setCustomerClassificationType(String customerClassificationType) {
        this.customerClassificationType = customerClassificationType;
    }

    public String getCustomerClassificationValue() {
        return customerClassificationValue;
    }

    public void setCustomerClassificationValue(String customerClassificationValue) {
        this.customerClassificationValue = customerClassificationValue;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
