package com.example.springbootpracticemall.model.dto;

import lombok.Data;

@Data
public class RoleOption {
    private Long id;
    private String chineseName;

    public RoleOption(Long id, String chineseName) {
        this.id = id;
        this.chineseName = chineseName;
    }
}
