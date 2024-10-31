package com.example.springbootpracticemall.model.dto;

import lombok.Data;

@Data
public class OptionDto {
    private String optionValue;
    private String displayName;

    public OptionDto(String optionValue, String displayName) {
        this.optionValue = optionValue;
        this.displayName = displayName;
    }
}
