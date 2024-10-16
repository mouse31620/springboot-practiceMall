package com.example.springbootpracticemall.controller;

import com.example.springbootpracticemall.model.dto.RoleOption;
import com.example.springbootpracticemall.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/roles")
    public List<RoleOption> getRoleOptions() {
        return roleService.getAllRoles().stream()
                .map(role -> new RoleOption(role.getId(), role.getRoleChinese()))
                .collect(Collectors.toList());
    }
}
