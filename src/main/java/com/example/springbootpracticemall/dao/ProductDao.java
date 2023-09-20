package com.example.springbootpracticemall.dao;

import com.example.springbootpracticemall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
}
