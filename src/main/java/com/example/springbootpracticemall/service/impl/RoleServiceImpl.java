package com.example.springbootpracticemall.service.impl;

import com.example.springbootpracticemall.model.entity.Role;
import com.example.springbootpracticemall.repository.RoleRepository;
import com.example.springbootpracticemall.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
