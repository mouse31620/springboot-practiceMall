package com.example.springbootpracticemall.security;

import com.example.springbootpracticemall.model.entity.Role;
import com.example.springbootpracticemall.model.entity.User;
import com.example.springbootpracticemall.util.Common;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final String id;
    private final String username;
    private final String password;
    private final String email;
    private final Role role;
    private final String customerType;

    public CustomUserDetails(User user) {
        this.id = Common.get(user.getId());
        this.username = user.getUserName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.role = user.getUserRole();
        this.customerType = user.getCustomerType().getTypeName();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getPrivileges().stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.getPrivilegeName()))
                .toList();
    }

    public String getId() { return id;}

    public String getCustomerType() { return customerType;}

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
