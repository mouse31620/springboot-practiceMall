package com.example.springbootpracticemall.service.impl;


import com.example.springbootpracticemall.model.dto.UserRegisterRequest;
import com.example.springbootpracticemall.model.entity.CustomerType;
import com.example.springbootpracticemall.model.entity.Role;
import com.example.springbootpracticemall.model.entity.User;
import com.example.springbootpracticemall.parameter.RoleType;
import com.example.springbootpracticemall.repository.CustomerTypeRepository;
import com.example.springbootpracticemall.repository.RoleRepository;
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
    private RoleRepository roleRepository;

    @Autowired
    private CustomerTypeRepository customerTypeRepository;

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
        Role commonUserRole = roleRepository.findByRoleName(RoleType.COMMON_USER.getTypeName())
                        .orElseThrow(() -> new RuntimeException("角色 COMMON_USER 未找到"));
        newUser.setUserRole(commonUserRole);
        CustomerType customerType = customerTypeRepository.findByTypeName(com.example.springbootpracticemall.parameter.CustomerType.REGULAR.getType())
                .orElseThrow(() -> new RuntimeException("顧客種類 REGULAR 未找到"));
        newUser.setCustomerType(customerType);
        newUser = userRepository.save(newUser);
        return newUser;
    }
}
