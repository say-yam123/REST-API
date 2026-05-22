package com.pka.demo.repository;

import com.pka.demo.entity.CustomerProofOfIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerProofOfIdRepository extends JpaRepository<CustomerProofOfIdEntity, Long> {
}
