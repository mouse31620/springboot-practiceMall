package com.example.springbootpracticemall.service;

import com.example.springbootpracticemall.model.dto.OrderRequest;
import com.example.springbootpracticemall.model.entity.Order;

public interface OrderService {

    Order createOrder(OrderRequest orderRequest);
}
