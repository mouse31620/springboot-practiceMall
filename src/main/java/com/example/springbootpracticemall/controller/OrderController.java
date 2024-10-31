package com.example.springbootpracticemall.controller;

import com.example.springbootpracticemall.model.dto.*;
import com.example.springbootpracticemall.model.entity.Order;
import com.example.springbootpracticemall.model.entity.Product;
import com.example.springbootpracticemall.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Validated
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderRequest orderRequest){
        Order order = orderService.createOrder(orderRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);

    }

    @GetMapping("/orders")
    public ResponseEntity<Page<OrderDto>> getOrders (
            @RequestParam(defaultValue = "0") Long orderId,
            @RequestParam(defaultValue = "0") Long userId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date searchStartDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date searchEndDate,
            @RequestParam(defaultValue = "") String orderState,
            @RequestParam(defaultValue = "createdDate") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(defaultValue = "12") @Max(100) @Min(0) Integer pageSize,
            @RequestParam(defaultValue = "1") @Min(0) Integer pageNumber
    ) {
        OrderQueryParam orderQueryParam = OrderQueryParam.builder()
                .orderId(orderId)
                .userId(userId)
                .searchStartDate(searchStartDate)
                .searchEndDate(searchEndDate)
                .orderState(orderState)
                .orderBy(orderBy)
                .sort(sort)
                .pageSize(pageSize)
                .pageNumber(pageNumber)
                .build();
        List<OrderDto> orderDtoList = orderService.getOrders(orderQueryParam);
        long totalCount = orderService.getOrderCount(orderQueryParam);
        int totalPages = totalCount == 0 ? 1 : ((int)totalCount + pageSize - 1)/pageSize;
        Page<OrderDto> page = Page.<OrderDto>builder()
                .pageSize(pageSize)
                .pageNumber(pageNumber)
                .totalItems(totalCount)
                .totalPages(totalPages)
                .result(orderDtoList)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/orders/{orderId}/products")
    public ResponseEntity<List<OrderProductResponse>> getOrderProducts(@PathVariable Long orderId) {
        List<OrderProductResponse> orderProducts = orderService.getOrderProducts(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderProducts);
    }

}
