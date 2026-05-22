package com.pka.demo.controller;
import com.pka.demo.entity.CustomerProofOfIdEntity;
import com.pka.demo.repository.CustomerProofOfIdRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
@RestController
@RequestMapping("/api/customerProofOfId")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerProofOfidController {
    @Autowired
    private CustomerProofOfIdRepository customerProofOfIdRepository;

    @GetMapping
    public List<CustomerProofOfIdEntity> getAllCustomerProofOfIds() {
        return customerProofOfIdRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerProofOfIdEntity> getCustomerProofOfIdById(@PathVariable Long id) {
        CustomerProofOfIdEntity customerProofOfId = customerProofOfIdRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Customer Proof of ID not found with id: " + id));
        return ResponseEntity.ok(customerProofOfId);
    }
    @PostMapping
    public ResponseEntity<CustomerProofOfIdEntity> createCustomerProofOfId(@RequestBody CustomerProofOfIdEntity customerProofOfId) {
        CustomerProofOfIdEntity savedCustomerProofOfId = customerProofOfIdRepository.save(customerProofOfId);
        return ResponseEntity.ok(savedCustomerProofOfId);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CustomerProofOfIdEntity> updateCustomerProofOfId(@PathVariable Long id, @RequestBody CustomerProofOfIdEntity customerProofOfIdDetails) {
        CustomerProofOfIdEntity existingCustomerProofOfId = customerProofOfIdRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Customer Proof of ID not found with id: " + id));

        existingCustomerProofOfId.setProofOfIdType(customerProofOfIdDetails.getProofOfIdType());
        existingCustomerProofOfId.setProofOfIdValue(customerProofOfIdDetails.getProofOfIdValue());
        existingCustomerProofOfId.setEffectiveDate(customerProofOfIdDetails.getEffectiveDate());
        existingCustomerProofOfId.setEndDate(customerProofOfIdDetails.getEndDate());
        existingCustomerProofOfId.setStartDate(customerProofOfIdDetails.getStartDate());

        CustomerProofOfIdEntity updatedCustomerProofOfId = customerProofOfIdRepository.save(existingCustomerProofOfId);
        return ResponseEntity.ok(updatedCustomerProofOfId);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerProofOfId(@PathVariable Long id) {
        CustomerProofOfIdEntity existingCustomerProofOfId = customerProofOfIdRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Customer Proof of ID not found with id: " + id));

        customerProofOfIdRepository.delete(existingCustomerProofOfId);
        return ResponseEntity.noContent().build();
    }
    
}
