package com.example.springbootpracticemall.service;

import com.example.springbootpracticemall.model.dto.ProductQueryParam;
import com.example.springbootpracticemall.model.dto.ProductRequest;
import com.example.springbootpracticemall.model.entity.Product;

import java.util.List;

public interface ProductService {

    Product getProductById(Long productId);

    List<Product> getProducts(ProductQueryParam param);

    Product createProduct(ProductRequest productRequest);

    long getProductsCount(ProductQueryParam param);
}
