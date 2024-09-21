package com.example.springbootpracticemall.parameter;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum RoleType {
    SUPER_ADMIN("SUPER_ADMIN"),
    ADMIN("ADMIN"),
    COMMON_USER("COMMON_USER");

    private String typeName;



}
