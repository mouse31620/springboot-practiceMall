package com.example.springbootpracticemall.controller;

import com.example.springbootpracticemall.model.dto.Page;
import com.example.springbootpracticemall.model.dto.ProductQueryParam;
import com.example.springbootpracticemall.model.dto.ProductRequest;
import com.example.springbootpracticemall.model.entity.Product;
import com.example.springbootpracticemall.parameter.ProductCategory;
import com.example.springbootpracticemall.service.ProductService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Locale;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "create_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(defaultValue = "12") @Max(100) @Min(0) Integer pageSize,
            @RequestParam(defaultValue = "0") @Min(0) Integer pageNumber
            ) {
        ProductQueryParam productQueryParam = ProductQueryParam.builder()
                .category(category)
                .search(search)
                .orderBy(orderBy)
                .sort(sort)
                .pageSize(pageSize)
                .pageNumber(pageNumber)
                .build();

        List<Product> productList = productService.getProducts(productQueryParam);
        long totalCount = productService.getProductsCount(productQueryParam);
        Page<Product> page = Page.<Product>builder()
                .pageSize(pageSize)
                .pageNumber(pageNumber)
                .total(totalCount)
                .result(productList)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Product product = productService.createProduct(productRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }
}
