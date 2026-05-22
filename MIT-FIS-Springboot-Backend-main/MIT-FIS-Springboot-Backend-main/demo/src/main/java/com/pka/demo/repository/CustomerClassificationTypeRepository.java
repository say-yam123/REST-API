package com.pka.demo.repository;
import com.pka.demo.entity.CustomerClassificationTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerClassificationTypeRepository extends JpaRepository<CustomerClassificationTypeEntity, Long> {
}