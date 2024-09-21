package com.example.springbootpracticemall.parameter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum CustomerType {
    REGULAR("REGULAR"),
    VIP("VIP");

    private String type;
}
