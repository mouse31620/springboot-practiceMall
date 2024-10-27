package com.example.springbootpracticemall.repository;

import com.example.springbootpracticemall.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
