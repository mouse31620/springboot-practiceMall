package com.example.springbootpracticemall.model.dto;

import lombok.Data;

@Data
public class OrderProductRequest {
    private Long productId;
    private Integer quantity;

    public OrderProductRequest(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
