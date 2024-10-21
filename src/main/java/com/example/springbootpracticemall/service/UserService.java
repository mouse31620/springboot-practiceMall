package com.example.springbootpracticemall.service;

import com.example.springbootpracticemall.model.dto.UserDto;
import com.example.springbootpracticemall.model.dto.UserQueryParam;
import com.example.springbootpracticemall.model.dto.UserRegisterRequest;
import com.example.springbootpracticemall.model.dto.UserRequest;
import com.example.springbootpracticemall.model.entity.User;

import java.util.List;

public interface UserService {

    User register(UserRegisterRequest userRegisterRequest);

    List<UserDto> getUsers(UserQueryParam param);

    long getUsersCount(UserQueryParam param);

    User getUserById(Long userId);

    User updateUser(Long userId, UserRequest userRequest);
}
