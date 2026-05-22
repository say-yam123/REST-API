package com.pka.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "CustomerDetail")
public class CustomerDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerIdentifier;  

    @Column(nullable = false)
    private String customerFullName;
    @Column(nullable = false)
    private String customerGender;
    
    @Column(nullable = false)
    private Long customerType;
    @Column(nullable = false)
    private LocalDate customerDateOfBirth;
    @Column(nullable = false)
    private String customerPreferredLanguage;
    @Column(nullable = false)
    private String customerStatus;
    @Column(nullable = false)
    private String customerCountryOfOrigin;
    public CustomerDetailEntity() {}
    public Long getCustomerIdentifier() {
        return customerIdentifier;
    }
    public void setCustomerIdentifier(Long customerIdentifier) {
        this.customerIdentifier = customerIdentifier;
    }
    public String getCustomerFullName() {
        return customerFullName;
    }
    public void setCustomerFullName(String customerFullName) {
        this.customerFullName = customerFullName;
    }
    public String getCustomerGender() {
        return customerGender;
    }
    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
    }
    public Long getCustomerType() {
        return customerType;
    }
    public void setCustomerType(Long customerType) {
        this.customerType = customerType;
    }
    public LocalDate getCustomerDateOfBirth() {
        return customerDateOfBirth;
    }
    public void setCustomerDateOfBirth(LocalDate customerDateOfBirth) {
        this.customerDateOfBirth = customerDateOfBirth;
    }
    public String getCustomerPreferredLanguage() {
        return customerPreferredLanguage;
    }
    public void setCustomerPreferredLanguage(String customerPreferredLanguage) {
        this.customerPreferredLanguage = customerPreferredLanguage;
    }
    public String getCustomerStatus() {
        return customerStatus;
    }
    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }
    public String getCustomerCountryOfOrigin() {
        return customerCountryOfOrigin;
    }
    public void setCustomerCountryOfOrigin(String customerCountryOfOrigin) {
        this.customerCountryOfOrigin = customerCountryOfOrigin;
    }
    
    
}
