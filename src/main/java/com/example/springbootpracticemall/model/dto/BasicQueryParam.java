package com.example.springbootpracticemall.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class BasicQueryParam {

    private String orderBy;
    private String sort;
    private Integer pageSize;
    private Integer pageNumber;
}
