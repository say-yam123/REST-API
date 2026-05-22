package com.pka.demo.controller;

import com.pka.demo.entity.CustomerContactEntity;
import com.pka.demo.repository.CustomerContactRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
@RestController
@RequestMapping("/api/customerContact")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerContactController {
    @Autowired
    private CustomerContactRepository customerContactRepository;

    @GetMapping
    public List<CustomerContactEntity> getAllCustomerContacts() {
        return customerContactRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerContactEntity> getCustomerContactById(@PathVariable Long id) {
        CustomerContactEntity customerContact = customerContactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Customer Contact not found with id: " + id));
        return ResponseEntity.ok(customerContact);
    }
    @PostMapping
    public ResponseEntity<CustomerContactEntity> createCustomerContact(@RequestBody CustomerContactEntity customerContact) {
        CustomerContactEntity savedCustomerContact = customerContactRepository.save(customerContact);
        return ResponseEntity.ok(savedCustomerContact);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CustomerContactEntity> updateCustomerContact(@PathVariable Long id, @RequestBody CustomerContactEntity customerContactDetails) {
        CustomerContactEntity existingCustomerContact = customerContactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Customer Contact not found with id: " + id));

        existingCustomerContact.setCustomerIdentifier(customerContactDetails.getCustomerIdentifier());
        existingCustomerContact.setCustomerContactType(customerContactDetails.getCustomerContactType());
        existingCustomerContact.setCustomerContactValue(customerContactDetails.getCustomerContactValue());
        existingCustomerContact.setEffectiveDate(customerContactDetails.getEffectiveDate());
        existingCustomerContact.setEndDate(customerContactDetails.getEndDate());
        existingCustomerContact.setStartDate(customerContactDetails.getStartDate());

        CustomerContactEntity updatedCustomerContact = customerContactRepository.save(existingCustomerContact);
        return ResponseEntity.ok(updatedCustomerContact);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerContact(@PathVariable Long id) {
        CustomerContactEntity existingCustomerContact = customerContactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Customer Contact not found with id: " + id));

        customerContactRepository.delete(existingCustomerContact);
        return ResponseEntity.noContent().build();
    }
    
}