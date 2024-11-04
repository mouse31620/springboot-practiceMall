package com.example.springbootpracticemall.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public JwtService jwtService(@Value("${jwt.secret-key}") String secretKeyStr, @Value("${jwt.valid-seconds}") int validSeconds) {
        return new JwtService(secretKeyStr, validSeconds);
    }

    // 密碼編碼器，用於加密和驗證密碼
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtAuthenticationFilter jwtAuthFilter) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/index.html", "/header.html", "/login.html", "/register.html", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/**", "/orders/**").permitAll()
                        .requestMatchers("/productCategories").permitAll()
                        .requestMatchers("/users/login", "/users/register").permitAll()
                        //產品管理權限
                        .requestMatchers(HttpMethod.POST, "/products/**").hasAuthority("PRODUCT_MANAGE")
                        .requestMatchers(HttpMethod.PUT, "/products/**").hasAuthority("PRODUCT_MANAGE")
                        .requestMatchers(HttpMethod.DELETE, "/products/**").hasAuthority("PRODUCT_MANAGE")

                        // 訂單管理權限
                        .requestMatchers(HttpMethod.POST, "/orders/**").hasAuthority("ORDER_MANAGE")
                        .requestMatchers(HttpMethod.PUT, "/orders/**").hasAuthority("ORDER_MANAGE")

                        // 用戶管理權限
                        .requestMatchers(HttpMethod.POST, "/users/**").hasAuthority("USER_MANAGE")
                        .requestMatchers(HttpMethod.PUT, "/users/**").hasAuthority("USER_MANAGE")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, BasicAuthenticationFilter.class)
                .build();
    }

}
