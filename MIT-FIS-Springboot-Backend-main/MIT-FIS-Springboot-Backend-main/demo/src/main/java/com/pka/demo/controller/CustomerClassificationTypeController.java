package com.pka.demo.controller;

import com.pka.demo.entity.CustomerClassificationTypeEntity;
import com.pka.demo.repository.CustomerClassificationTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/customerClassificationTypes")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerClassificationTypeController {

    @Autowired
    private CustomerClassificationTypeRepository repository;

    // GET ALL
    @GetMapping
    public List<CustomerClassificationTypeEntity> getAll() {
        return repository.findAll();
    }

    // GET ONE
    @GetMapping("/{id}")
    public CustomerClassificationTypeEntity getById(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(NOT_FOUND,
                                "CustomerClassificationType not found: " + id));
    }

    // CREATE
    @PostMapping
    public ResponseEntity<CustomerClassificationTypeEntity> create(
            @RequestBody CustomerClassificationTypeEntity entity) {

        entity.setCustomerClassificationId(null); // ensure insert

        CustomerClassificationTypeEntity saved = repository.save(entity);

        return ResponseEntity.ok(saved);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CustomerClassificationTypeEntity> update(
            @PathVariable Long id,
            @RequestBody CustomerClassificationTypeEntity dto) {

        CustomerClassificationTypeEntity existing = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(NOT_FOUND,
                                "CustomerClassificationType not found: " + id));

        existing.setCustomerClassificationType(dto.getCustomerClassificationType());
        existing.setCustomerClassificationValue(dto.getCustomerClassificationValue());
        existing.setEffectiveDate(dto.getEffectiveDate());

        CustomerClassificationTypeEntity updated = repository.save(existing);

        return ResponseEntity.ok(updated);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        if (!repository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND,
                    "CustomerClassificationType not found: " + id);
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}