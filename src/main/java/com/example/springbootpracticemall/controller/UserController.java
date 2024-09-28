package com.example.springbootpracticemall.controller;

import com.example.springbootpracticemall.model.dto.LoginResponse;
import com.example.springbootpracticemall.model.dto.UserDto;
import com.example.springbootpracticemall.model.dto.UserLoginRequest;
import com.example.springbootpracticemall.model.dto.UserRegisterRequest;
import com.example.springbootpracticemall.model.entity.User;
import com.example.springbootpracticemall.security.CustomUserDetails;
import com.example.springbootpracticemall.security.JwtService;
import com.example.springbootpracticemall.security.UserDetailsServiceImpl;
import com.example.springbootpracticemall.service.UserService;
import com.example.springbootpracticemall.util.CookieUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String JWT = "jwt";

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        User user = userService.register(userRegisterRequest);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid UserLoginRequest userLoginRequest) {

        CustomUserDetails user = userDetailsService.loadUserByUsername(userLoginRequest.getEmail());
        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())){
            logger.warn("email{}的密碼不正確", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "密碼不正確");
        }
        String jwt = jwtService.createLoginAccessToken(user);

        return new LoginResponse(jwt, user);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        SecurityContextHolder.clearContext();
        return ResponseEntity.status(HttpStatus.OK).body("Logged out successfully");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(HttpServletRequest request){
        Optional<String> jwtOption = CookieUtil.getCookieValue(request, JWT);
        if (jwtOption.isPresent()){
            String jwt = jwtOption.get();
            Claims claims;
            try {
                claims =  jwtService.parseToken(jwt);
            } catch (JwtException e){
                logger.error(e.getMessage());
                throw new BadCredentialsException(e.getMessage(), e);
            }
            UserDto userDto = new UserDto();
            if (claims != null){
                userDto = UserDto.builder()
                        .userName(claims.get("username", String.class))
                        .email(claims.get("email", String.class))
                        .customerType(claims.get("customerType", String.class))
                        .build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未登入");
    }
}
