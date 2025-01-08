package com.example.springbootpracticemall.service.impl;

import com.example.springbootpracticemall.model.dto.OrderProductRequest;
import com.example.springbootpracticemall.model.dto.OrderRequest;
import com.example.springbootpracticemall.model.entity.Order;
import com.example.springbootpracticemall.model.entity.Product;
import com.example.springbootpracticemall.repository.OrderRepository;
import com.example.springbootpracticemall.repository.ProductRepository;
import com.example.springbootpracticemall.service.OrderService;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedissonClient redisson;

    @Test
    void verifyDatabaseType() throws SQLException {
        String databaseUrl = jdbcTemplate.getDataSource().getConnection().getMetaData().getURL();
        System.out.println("Connected to: " + databaseUrl);
        assertTrue(databaseUrl.contains("jdbc:h2"), "未連接到 H2 資料庫");
    }

    @Test
    void createOrderWithMultiThread() throws InterruptedException {

        Long productId = 1L; // 測試用產品ID
        int initialStock = 50;
        Product product = productRepository.findById(productId).orElseThrow();
        product.setStock(initialStock);
        productRepository.save(product);

        // 設置Redis初始庫存
        String stockRedisKey = "product:stock:" + productId;
        redisTemplate.opsForValue().set(stockRedisKey, String.valueOf(initialStock));

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

        // 驗證 Redis 中的庫存值是否一致
        String redisStock = redisTemplate.opsForValue().get(stockRedisKey);
        assertEquals(String.valueOf(expectedStock), redisStock, "Redis 庫存值不正確");

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

    private Long createTestOrder(long productId, int orderProductQuantity) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderUserId(1L);
        List<OrderProductRequest> orderProducts = new ArrayList<OrderProductRequest>();
        orderProducts.add(new OrderProductRequest(productId, orderProductQuantity));
        orderRequest.setProducts(orderProducts);
        Order order =  orderService.createOrder(orderRequest);

        return order.getId();
    }

    @Test
    public void testDeleteOrder() {
        long productId = 1L;//測試用訂單id
        // 初始化測試數據，創建一個訂單
        Long orderId = createTestOrder(productId,2); // 假設有方法生成測試訂單

        // 刪除訂單
        orderService.deleteOrder(orderId);

        Product afterDeleteProduct = productRepository.findById(productId).orElseThrow();
        System.out.println(afterDeleteProduct.getStock());

        // 檢查訂單是否已刪除
        assertThrows(ResponseStatusException.class, () -> orderService.deleteOrder(orderId),
                "訂單已刪除，應該拋出異常");
    }

    @Test
    public void testMultiThreadDeleteOrder() throws InterruptedException {
        long productId = 1L;//測試用訂單id
        int orderProductQuantity = 5;//訂單數量
        // 初始化測試數據，創建一個訂單
        Long orderId = createTestOrder(productId, orderProductQuantity);
        //測試
        Product beforeDeleteProduct = productRepository.findById(productId).orElseThrow();
        int beforeDeleteStock = beforeDeleteProduct.getStock();

        int numberOfThreads = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    orderService.deleteOrder(orderId);
                } catch (ResponseStatusException e) {
                    System.out.println("訂單已刪除或找不到，錯誤信息：" + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(30, TimeUnit.SECONDS);
        executorService.shutdown();

        // 最後確認訂單已刪除
        assertThrows(ResponseStatusException.class, () -> orderService.deleteOrder(orderId),
                "訂單已刪除，應該拋出異常");
        //確認庫存數量正常
        Product afterDeleteProduct = productRepository.findById(productId).orElseThrow();
        int expectedStock = beforeDeleteStock + orderProductQuantity;
        assertEquals(expectedStock, afterDeleteProduct.getStock(), "庫存復原不正確");
    }
}