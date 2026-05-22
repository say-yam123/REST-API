package com.pka.demo.entity;
import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(name = "CustomerContactInformation")
public class CustomerContactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactInformationId;
    @Column(name = "customerIdentifier", nullable = false)
    private Long customerIdentifier;
    @Column(name = "CustomerContactType",nullable = false)
    private String customerContactType;
    @Column(name = "CustomerContactValue",nullable = false)
    private String customerContactValue;
    @Column(name = "EffectiveDate",nullable = false)
    private LocalDate effectiveDate;
    @Column(name = "EndDate",nullable = false)
    private LocalDate endDate;
    @Column(name = "StartDate",nullable = false)
    private LocalDate startDate;
    public CustomerContactEntity() {
    }
    public CustomerContactEntity(Long customerIdentifier, String customerContactType, String customerContactValue, LocalDate effectiveDate, LocalDate endDate, LocalDate startDate) {
        this.customerIdentifier = customerIdentifier;
        this.customerContactType = customerContactType;
        this.customerContactValue = customerContactValue;
        this.effectiveDate = effectiveDate;
        this.endDate = endDate;
        this.startDate = startDate;
    }
    public Long getCustomerIdentifier() {
        return customerIdentifier;
    }
    public void setCustomerIdentifier(Long customerIdentifier) {
        this.customerIdentifier = customerIdentifier;
    }
    public String getCustomerContactType() {
        return customerContactType;
    }
    public void setCustomerContactType(String customerContactType) {
        this.customerContactType = customerContactType;
    }
    public String getCustomerContactValue() {
        return customerContactValue;
    }
    public void setCustomerContactValue(String customerContactValue) {
        this.customerContactValue = customerContactValue;
    }
    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }
    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public Long getContactInformationId() {
        return contactInformationId;
    }
    public void setContactInformationId(Long contactInformationId) {
        this.contactInformationId = contactInformationId;
    }
    
    
}
