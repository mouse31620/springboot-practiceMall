package com.example.springbootpracticemall.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class OrderQueryParam extends BasicQueryParam {
    private Long orderId;
    private Long userId;
    private Date searchStartDate;
    private Date searchEndDate;
    private String orderState;
}
