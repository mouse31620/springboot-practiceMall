package com.example.springbootpracticemall.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class ProductQueryParam extends BasicQueryParam {
    private String category;
    private String search;
}
