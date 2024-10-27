package com.example.springbootpracticemall.service.impl;

import com.example.springbootpracticemall.model.dto.OrderRequest;
import com.example.springbootpracticemall.model.entity.Order;
import com.example.springbootpracticemall.model.entity.Product;
import com.example.springbootpracticemall.model.entity.User;
import com.example.springbootpracticemall.repository.OrderRepository;
import com.example.springbootpracticemall.repository.ProductRepository;
import com.example.springbootpracticemall.service.OrderService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public Order createOrder(OrderRequest orderRequest) {
        Order newOrder = new Order();
        User orderUser = new User();
        orderUser.setId(orderRequest.getOrderUserId());
        newOrder.setOrderUser(orderUser);
        newOrder.setReceiverName(orderRequest.getReceiverName());
        newOrder.setReceiverEmail(orderRequest.getReceiverEmail());
        newOrder.setReceiverAddress(orderRequest.getReceiverAddress());
        newOrder.setOrderPrice(orderRequest.getOrderPrice());
        Set<Product> products = orderRequest.getProducts().stream()
                        .map(productId -> productRepository.findById(productId)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "無法找到 ID 為 " + productId + " 的產品")))
                        .collect(Collectors.toSet());
        newOrder.setProducts(products);
        return orderRepository.save(newOrder);
    }
}
