package com.example.springbootpracticemall.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Page<T> {
    private Integer totalPages;
    private Integer pageSize;
    private Integer pageNumber;
    private long totalItems;
    private List<T> result;
}
