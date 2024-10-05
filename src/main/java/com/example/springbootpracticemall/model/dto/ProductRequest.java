package com.example.springbootpracticemall.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ProductRequest {
    @NotNull
    private String productName;

    @NotNull
    private String category;

    @NotNull
    private String imageUrl;

    @NotNull
    private Integer price;

    @NotNull
    private Integer stock;
    private String description;


}
