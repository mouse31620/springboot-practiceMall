package com.example.springbootpracticemall.service;

import com.example.springbootpracticemall.dto.ProductRequest;
import com.example.springbootpracticemall.model.Product;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
