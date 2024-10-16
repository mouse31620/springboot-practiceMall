package com.example.springbootpracticemall.service.impl;

import com.example.springbootpracticemall.model.entity.CustomerType;
import com.example.springbootpracticemall.repository.CustomerTypeRepository;
import com.example.springbootpracticemall.service.CustomerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerTypeServiceImpl implements CustomerTypeService {

    @Autowired
    CustomerTypeRepository customerTypeRepository;

    public List<CustomerType> getAllCategories() {
        return customerTypeRepository.findAll();
    }
}
