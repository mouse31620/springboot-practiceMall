package com.example.springbootpracticemall.parameter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum OrderState {
    PENDING("訂單送出"),
    SHIPPING("出貨中"),
    SHIPPED("已出貨"),
    COMPLETED("完成訂單");

    private String description;
}
