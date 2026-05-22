package com.pka.demo.controller;

import com.pka.demo.entity.CustomerDetailEntity;
import com.pka.demo.repository.CustomerDetailRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
@RestController
@RequestMapping("/api/customerDetails")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerDetailController {
    @Autowired
    private CustomerDetailRepository repository;

    // GET ALL
    @GetMapping
    public List<CustomerDetailEntity> getAll() {
        return repository.findAll();
    }

    // GET ONE
    @GetMapping("/{id}")
    public CustomerDetailEntity getById(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(NOT_FOUND,
                                "CustomerDetail not found: " + id));
    }

    // CREATE
    @PostMapping
    public ResponseEntity<CustomerDetailEntity> create(
            @RequestBody CustomerDetailEntity entity) {

        entity.setCustomerIdentifier(null); // ensure insert

        CustomerDetailEntity saved = repository.save(entity);

        return ResponseEntity.ok(saved);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDetailEntity> update(
            @PathVariable Long id,
            @RequestBody CustomerDetailEntity dto) {

        CustomerDetailEntity existing = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(NOT_FOUND,
                                "CustomerDetail not found: " + id));

        dto.setCustomerIdentifier(existing.getCustomerIdentifier()); // ensure update

        CustomerDetailEntity saved = repository.save(dto);

        return ResponseEntity.ok(saved);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        CustomerDetailEntity existing = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(NOT_FOUND,
                                "CustomerDetail not found: " + id));

        repository.delete(existing);

        return ResponseEntity.noContent().build();
    }
}
