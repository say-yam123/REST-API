package com.pka.demo.repository;
import com.pka.demo.entity.CustomerContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerContactRepository extends JpaRepository<CustomerContactEntity, Long> {
}

