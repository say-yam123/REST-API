package com.pka.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CustomerAddress")
public class CustomerAddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @Column(name = "customerIdentifier", nullable = false)
    private Long customerIdentifier;

    @Column(name = "customerAddressType")
    private String customerAddressType;

    @Column(name = "customerAddressValue")
    private String customerAddressValue;

    @Column(name = "effectiveDate")
    private LocalDate effectiveDate;

    public CustomerAddressEntity() {
    }

    public Long getAddressId() {
        return addressId;
    }
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
    public Long getCustomerIdentifier() {
        return customerIdentifier;
    }
    public void setCustomerIdentifier(Long customerIdentifier) {
        this.customerIdentifier = customerIdentifier;
    }
    public String getCustomerAddressType() {
        return customerAddressType;
    }
    public void setCustomerAddressType(String customerAddressType) {
        this.customerAddressType = customerAddressType;
    }
    public String getCustomerAddressValue() {
        return customerAddressValue;
    }
    public void setCustomerAddressValue(String customerAddressValue) {
        this.customerAddressValue = customerAddressValue;
    }
    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }
    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    
}
