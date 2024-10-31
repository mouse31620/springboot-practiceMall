package com.example.springbootpracticemall.controller;

import com.example.springbootpracticemall.model.dto.OptionDto;
import com.example.springbootpracticemall.parameter.OrderState;
import com.example.springbootpracticemall.parameter.ProductCategory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OptionController {

    @GetMapping("/productCategories")
    public ResponseEntity<List<OptionDto>> getProductCategoryOption() {
        List<OptionDto> categoryOptions = Arrays.stream(ProductCategory.values())
                .map(category -> new OptionDto(category.name(), category.getDescription()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(categoryOptions);
    }

    @GetMapping("/orderStates")
    public ResponseEntity<List<OptionDto>> getOrderStateOption() {
        List<OptionDto> orderStateOptions = Arrays.stream(OrderState.values())
                .map(state -> new OptionDto(state.name(), state.getDescription()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(orderStateOptions);
    }
}
