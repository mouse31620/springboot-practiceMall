package com.example.springbootpracticemall.service.impl;

import ch.qos.logback.classic.Level;
import com.example.springbootpracticemall.model.dto.OrderProductRequest;
import com.example.springbootpracticemall.model.dto.OrderRequest;
import com.example.springbootpracticemall.model.entity.Product;
import com.example.springbootpracticemall.repository.ProductRepository;
import com.example.springbootpracticemall.service.OrderService;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void verifyDatabaseType() throws SQLException {
        String databaseUrl = jdbcTemplate.getDataSource().getConnection().getMetaData().getURL();
        System.out.println("Connected to: " + databaseUrl);
        assertTrue(databaseUrl.contains("jdbc:h2"), "未連接到 H2 資料庫");
    }

    @Test
    void createOrder() throws InterruptedException {

        Long productId = 1L; // 測試用產品ID
        int initialStock = 50;
        Product product = productRepository.findById(productId).orElseThrow();
        product.setStock(initialStock);
        productRepository.save(product);

        // 模擬並發創建訂單
        int numberOfThreads = 10;
        int quantityPerOrder = 5;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() ->{
                OrderRequest orderRequest = new OrderRequest();
                orderRequest.setOrderUserId(1L);
                List<OrderProductRequest> orderProducts = new ArrayList<OrderProductRequest>();
                orderProducts.add(new OrderProductRequest(productId, quantityPerOrder));
                orderRequest.setProducts(orderProducts);
                try {
                    orderService.createOrder(orderRequest);
                } catch (Exception e) {
                    System.out.println("訂單創建失敗：" + e.getMessage());
                    System.out.println("錯誤種類 : " + e.getClass().getName());
                } finally {
                    latch.countDown();
                }
            });
        }
        // 等待所有線程結束
        latch.await(30, TimeUnit.SECONDS);
        executorService.shutdown();

        // 檢查產品庫存是否正確減少
        Product updatedProduct = productRepository.findById(productId).orElseThrow();
        int expectedStock = initialStock - (numberOfThreads * quantityPerOrder);
        assertEquals(expectedStock, updatedProduct.getStock(), "庫存減少不正確");

        // 如果庫存不足，檢查拋出異常
        OrderRequest insufficientOrderRequest = new OrderRequest();
        insufficientOrderRequest.setOrderUserId(1L);
        List<OrderProductRequest> orderProducts = new ArrayList<OrderProductRequest>();
        orderProducts.add(new OrderProductRequest(productId, quantityPerOrder));
        insufficientOrderRequest.setProducts(orderProducts);
        assertThrows(ResponseStatusException.class, () -> {
            orderService.createOrder(insufficientOrderRequest);
        }, "應該拋出庫存不足異常");
    }
}