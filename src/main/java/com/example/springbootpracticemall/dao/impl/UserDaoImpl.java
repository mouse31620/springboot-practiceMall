package com.example.springbootpracticemall.dao.impl;

import com.example.springbootpracticemall.dao.UserDao;
import com.example.springbootpracticemall.model.dto.UserRegisterRequest;
import com.example.springbootpracticemall.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createUser(UserRegisterRequest registerRequest) {
        User user = new User();
        return null;
    }
}
