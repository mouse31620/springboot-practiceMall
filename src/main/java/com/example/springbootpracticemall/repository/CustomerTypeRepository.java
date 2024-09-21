package com.example.springbootpracticemall.repository;

import com.example.springbootpracticemall.model.entity.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerTypeRepository extends JpaRepository<CustomerType, Long> {

    Optional<CustomerType> findByTypeName(String typeName);
}
