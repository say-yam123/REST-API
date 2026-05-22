package com.pka.demo.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "CustomerProofOfId")
public class CustomerProofOfIdEntity {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "proofId")
private Long proofId;

@Column(nullable = false)
private Long customerIdentifier;
@Column(name = "proofOfIdType", nullable = false)
private String proofOfIdType;

@Column(name = "proofOfIdValue", nullable = false)
private String proofOfIdValue;
@Column(name = "effectiveDate", nullable = false)
private LocalDate effectiveDate;

@Column(name = "endDate", nullable = false)
private LocalDate endDate;

@Column(name = "startDate", nullable = false)
private LocalDate startDate;

    public CustomerProofOfIdEntity() {}
    public CustomerProofOfIdEntity(Long customerIdentifier, String proofOfIdType, String proofOfIdValue, LocalDate effectiveDate, LocalDate endDate, LocalDate startDate) {
        this.customerIdentifier = customerIdentifier;
        this.proofOfIdType = proofOfIdType;
        this.proofOfIdValue = proofOfIdValue;
        this.effectiveDate = effectiveDate;
        this.endDate = endDate;
        this.startDate = startDate;
    }
    public Long getProofId() {
        return proofId;
    }
    public void setProofId(Long proofId) {
        this.proofId = proofId;
    }
    public Long getCustomerIdentifier() {
        return customerIdentifier;
    }
    public void setCustomerIdentifier(Long customerIdentifier) {
        this.customerIdentifier = customerIdentifier;
    }
    public String getProofOfIdType() {
        return proofOfIdType;
    }
    public void setProofOfIdType(String proofOfIdType) {
        this.proofOfIdType = proofOfIdType;
    }
    public String getProofOfIdValue() {
        return proofOfIdValue;
    }
    public void setProofOfIdValue(String proofOfIdValue) {
        this.proofOfIdValue = proofOfIdValue;
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
    
}
