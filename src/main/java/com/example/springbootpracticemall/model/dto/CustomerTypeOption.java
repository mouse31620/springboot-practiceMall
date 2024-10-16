package com.example.springbootpracticemall.model.dto;

import lombok.Data;

@Data
public class CustomerTypeOption {
    private Long id;
    private String typeChinese;

    public CustomerTypeOption(Long id, String typeChinese) {
        this.id = id;
        this.typeChinese = typeChinese;
    }
}
