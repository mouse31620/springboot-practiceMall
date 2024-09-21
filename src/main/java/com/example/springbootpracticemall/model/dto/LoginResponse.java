package com.example.springbootpracticemall.model.dto;

import com.example.springbootpracticemall.security.CustomUserDetails;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
public class LoginResponse {
    private String jwt;
    private String userName;
    private String customerType;
    private List<String> authorities;

    public LoginResponse (String jwt, CustomUserDetails userDetails){
        this.jwt = jwt;
        this.userName = userDetails.getUsername();
        this.customerType = userDetails.getCustomerType();
        this.authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }
}
