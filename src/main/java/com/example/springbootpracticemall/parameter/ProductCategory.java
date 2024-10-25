package com.example.springbootpracticemall.parameter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ProductCategory {
    FOOD("食品"),
    CAR("車"),
    BOOK("書籍");

    private String description;
}
