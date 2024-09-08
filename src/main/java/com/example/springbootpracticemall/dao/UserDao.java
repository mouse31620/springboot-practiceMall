package com.example.springbootpracticemall.dao;

import com.example.springbootpracticemall.model.dto.UserRegisterRequest;

public interface UserDao {

    Integer createUser(UserRegisterRequest registerRequest);
}
