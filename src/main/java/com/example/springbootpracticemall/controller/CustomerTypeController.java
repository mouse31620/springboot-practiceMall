package com.example.springbootpracticemall.controller;

import com.example.springbootpracticemall.model.dto.CustomerTypeOption;
import com.example.springbootpracticemall.service.CustomerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CustomerTypeController {
    @Autowired
    private CustomerTypeService customerTypeService;

    @GetMapping("/customerTypes")
    public List<CustomerTypeOption> getCustomerTypeOptions() {
        return customerTypeService.getAllCategories().stream()
                .map(type -> new CustomerTypeOption(type.getId(), type.getTypeChinese()))
                .collect(Collectors.toList());
    }
}
