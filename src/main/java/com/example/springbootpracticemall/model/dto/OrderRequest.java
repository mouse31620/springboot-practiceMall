package com.example.springbootpracticemall.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    @NotNull
    private Long orderUserId;
    @NotNull
    private List<OrderProductRequest> products;
    @NotNull
    private String receiverName;
    @NotNull
    private String receiverEmail;
    @NotNull
    private String receiverAddress;
    @NotNull
    private Long orderPrice;
}
