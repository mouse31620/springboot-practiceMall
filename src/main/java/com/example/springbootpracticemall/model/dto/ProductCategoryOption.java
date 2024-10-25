package com.example.springbootpracticemall.model.dto;

import lombok.Data;

@Data
public class ProductCategoryOption {
    private String optionValue;
    private String displayName;

    public ProductCategoryOption(String optionValue, String displayName) {
        this.optionValue = optionValue;
        this.displayName = displayName;
    }
}
