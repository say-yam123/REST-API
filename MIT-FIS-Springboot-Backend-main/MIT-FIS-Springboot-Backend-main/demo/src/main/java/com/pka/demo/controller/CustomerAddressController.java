package com.pka.demo.controller;

import com.pka.demo.entity.CustomerAddressEntity;
import com.pka.demo.repository.CustomerAddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/customerAddress")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerAddressController {
    @Autowired
    private CustomerAddressRepository repository;

    // GET ALL
    @GetMapping
    public List<CustomerAddressEntity> getAll() {
        return repository.findAll();
    }

    // GET ONE
    @GetMapping("/{id}")
    public CustomerAddressEntity getById(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(NOT_FOUND,
                                "CustomerAddress not found: " + id));
    }

    // CREATE
    @PostMapping
    public ResponseEntity<CustomerAddressEntity> create(
            @RequestBody CustomerAddressEntity entity) {

        entity.setAddressId(null); // ensure insert

        CustomerAddressEntity saved = repository.save(entity);

        return ResponseEntity.ok(saved);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CustomerAddressEntity> update(
            @PathVariable Long id,
            @RequestBody CustomerAddressEntity dto) {

        CustomerAddressEntity existing = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(NOT_FOUND,
                                "CustomerAddress not found: " + id));

        existing.setCustomerIdentifier(dto.getCustomerIdentifier());
        existing.setCustomerAddressType(dto.getCustomerAddressType());
        existing.setCustomerAddressValue(dto.getCustomerAddressValue());
        existing.setEffectiveDate(dto.getEffectiveDate());

        CustomerAddressEntity updated = repository.save(existing);
        return ResponseEntity.ok(updated);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        CustomerAddressEntity existing = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(NOT_FOUND,
                                "CustomerAddress not found: " + id));

        repository.delete(existing);
        return ResponseEntity.noContent().build();
    }
}