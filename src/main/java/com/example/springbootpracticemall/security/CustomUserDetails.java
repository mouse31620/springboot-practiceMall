package com.example.springbootpracticemall.security;

import com.example.springbootpracticemall.model.entity.Role;
import com.example.springbootpracticemall.model.entity.User;
import com.example.springbootpracticemall.util.Common;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private String id;
    private String username;
    private String password;
    private String email;
    private Set<Role> roles;
    private String customerType;

    public CustomUserDetails(User user) {
        this.id = Common.get(user.getId());
        this.username = user.getUserName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.roles = user.getUserRoles();
        this.customerType = user.getCustomerType().getTypeName();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE" + role.getRoleName()))
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
