package com.example.springbootpracticemall.controller;

import com.example.springbootpracticemall.model.dto.ProductCategoryOption;
import com.example.springbootpracticemall.parameter.ProductCategory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OptionController {

    @GetMapping("/productCategories")
    public ResponseEntity<List<ProductCategoryOption>> getProductCategoryOption() {
        List<ProductCategoryOption> categoryOptions = Arrays.stream(ProductCategory.values())
                .map(category -> new ProductCategoryOption(category.name(), category.getDescription()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(categoryOptions);
    }
}
