package com.example.springbootpracticemall.controller;

import com.example.springbootpracticemall.model.CustomUserDetails;
import com.example.springbootpracticemall.model.dto.LoginResponse;
import com.example.springbootpracticemall.model.dto.UserDto;
import com.example.springbootpracticemall.model.dto.UserLoginRequest;
import com.example.springbootpracticemall.model.dto.UserRegisterRequest;
import com.example.springbootpracticemall.model.entity.User;
import com.example.springbootpracticemall.security.JwtService;
import com.example.springbootpracticemall.service.UserService;
import com.example.springbootpracticemall.security.UserDetailsServiceImpl;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

import java.util.Map;

@RestController
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/users/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        User user = userService.register(userRegisterRequest);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PostMapping("/users/login")
    public LoginResponse login(@RequestBody @Valid UserLoginRequest userLoginRequest) {

        CustomUserDetails user = userDetailsService.loadUserByUsername(userLoginRequest.getEmail());
        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())){
            logger.warn("email{}的密碼不正確", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "密碼不正確");
        }
        String jwt = jwtService.createLoginAccessToken(user);

        return new LoginResponse(jwt, user);
    }

    @GetMapping("/who-am-i")
    public Map<String, Object> whoAmI(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        String jwt = authorization.substring(BEARER_PREFIX.length());
        try {
            return jwtService.parseToken(jwt);
        } catch (JwtException e){
            e.printStackTrace();
            throw new BadCredentialsException(e.getMessage(), e);
        }
    }
}
