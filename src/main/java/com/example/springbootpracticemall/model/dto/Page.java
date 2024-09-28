package com.example.springbootpracticemall.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Page<T> {
    private Integer pageSize;
    private Integer pageNumber;
    private long total;
    private List<T> result;
}
