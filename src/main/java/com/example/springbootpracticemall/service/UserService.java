package com.example.springbootpracticemall.service;

import com.example.springbootpracticemall.model.dto.UserRegisterRequest;
import com.example.springbootpracticemall.model.entity.User;

public interface UserService {

    User register(UserRegisterRequest userRegisterRequest);
}
