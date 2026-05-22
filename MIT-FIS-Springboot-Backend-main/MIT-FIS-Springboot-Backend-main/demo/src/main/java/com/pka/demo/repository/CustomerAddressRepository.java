package com.pka.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pka.demo.entity.CustomerAddressEntity;


public interface CustomerAddressRepository extends JpaRepository<CustomerAddressEntity, Long> {

}
