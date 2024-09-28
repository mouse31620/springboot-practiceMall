package com.example.springbootpracticemall.model.dto;

import com.example.springbootpracticemall.parameter.ProductCategory;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class ProductQueryParam extends BasicQueryParam {
    private ProductCategory category;
    private String search;
}
