package com.example.springbootpracticemall.service;

import com.example.springbootpracticemall.model.dto.OrderDto;
import com.example.springbootpracticemall.model.dto.OrderProductResponse;
import com.example.springbootpracticemall.model.dto.OrderQueryParam;
import com.example.springbootpracticemall.model.dto.OrderRequest;
import com.example.springbootpracticemall.model.entity.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(OrderRequest orderRequest);

    Order getOrderById(Long orderId);

    Order updateOrder(Long orderId, OrderRequest orderRequest);

    long getOrderCount(OrderQueryParam orderQueryParam);

    List<OrderDto> getOrders(OrderQueryParam orderQueryParam);

    List<OrderProductResponse> getOrderProducts(Long orderId);

}
