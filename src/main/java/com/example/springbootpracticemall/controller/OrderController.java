package com.example.springbootpracticemall.controller;

import com.example.springbootpracticemall.model.dto.OrderRequest;
import com.example.springbootpracticemall.model.dto.ProductRequest;
import com.example.springbootpracticemall.model.entity.Order;
import com.example.springbootpracticemall.model.entity.Product;
import com.example.springbootpracticemall.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderRequest orderRequest){
        Order order = orderService.createOrder(orderRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);

    }
}
