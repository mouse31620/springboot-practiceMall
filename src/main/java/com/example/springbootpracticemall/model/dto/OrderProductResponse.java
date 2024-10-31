package com.example.springbootpracticemall.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderProductResponse {

    private String productName;
    private String quantity;
    private String price;
}
