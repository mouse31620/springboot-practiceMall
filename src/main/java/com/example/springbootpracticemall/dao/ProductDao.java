package com.example.springbootpracticemall.dao;

import com.example.springbootpracticemall.dto.ProductRequest;
import com.example.springbootpracticemall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
