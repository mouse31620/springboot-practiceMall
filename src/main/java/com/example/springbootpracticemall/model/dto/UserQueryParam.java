package com.example.springbootpracticemall.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class UserQueryParam extends BasicQueryParam {
    private String search;
    private String customerType;
    private String role;
}
