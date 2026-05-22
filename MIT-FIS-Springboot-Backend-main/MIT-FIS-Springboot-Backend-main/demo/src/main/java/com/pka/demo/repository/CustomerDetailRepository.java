package com.pka.demo.repository;
import com.pka.demo.entity.CustomerDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerDetailRepository extends JpaRepository<CustomerDetailEntity, Long> {
}
