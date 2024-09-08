package com.example.springbootpracticemall.service.impl;


import com.example.springbootpracticemall.model.dto.UserDto;
import com.example.springbootpracticemall.model.dto.UserLoginRequest;
import com.example.springbootpracticemall.model.dto.UserRegisterRequest;
import com.example.springbootpracticemall.model.entity.User;
import com.example.springbootpracticemall.repository.UserRepository;
import com.example.springbootpracticemall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(UserRegisterRequest userRegisterRequest) {
        Optional<User> user = userRepository.findUserByEmail(userRegisterRequest.getEmail());
        if (user.isPresent()) {
            logger.warn("該email{}已經被註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "該email已經被註冊");
        }
        User newUser = new User();
        BeanUtils.copyProperties(userRegisterRequest, newUser);
        newUser.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        Date now = new Date();
        newUser.setCreatedDate(now);
        newUser.setLastModifiedDate(now);
        newUser.setUserRole(1);
        newUser = userRepository.save(newUser);
        return newUser;
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userRepository.findUserByEmail(userLoginRequest.getEmail())
                .orElseThrow(() -> {
                    logger.warn("該email{}未註冊", userLoginRequest.getEmail());
                    return new ResponseStatusException(HttpStatus.BAD_REQUEST);
                });
        if (user.getPassword().equals(passwordEncoder.encode(userLoginRequest.getPassword()))){
            return user;
        } else {
            logger.warn("email{}的密碼不正確", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "密碼不正確");
        }
    }
}
