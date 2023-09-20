package com.example.springbootpracticemall.service.impl;

import com.example.springbootpracticemall.dao.ProductDao;
import com.example.springbootpracticemall.model.Product;
import com.example.springbootpracticemall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}
