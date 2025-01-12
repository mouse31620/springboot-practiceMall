package com.example.springbootpracticemall.service.impl;

import com.example.springbootpracticemall.model.dto.ProductQueryParam;
import com.example.springbootpracticemall.model.dto.ProductRequest;
import com.example.springbootpracticemall.model.entity.Product;
import com.example.springbootpracticemall.model.entity.QProduct;
import com.example.springbootpracticemall.repository.ProductRepository;
import com.example.springbootpracticemall.service.ProductService;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String STOCK_KEY_PREFIX = "product:stock:";

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("未找到該商品"));
    }

    @Override
    public List<Product> getProducts(ProductQueryParam param) {
        QProduct product = QProduct.product;
        BooleanExpression namePredicate = (!"".equals(param.getSearch())) ? product.productName.containsIgnoreCase(param.getSearch()) : null;
        BooleanExpression categoryPredicate = (!"".equals(param.getCategory())) ? product.category.eq(param.getCategory()) : null;

        Order order = param.getSort().equalsIgnoreCase("asc") ? Order.ASC : Order.DESC;
        Path<String> orderByPath  =  Expressions.stringPath(product, param.getOrderBy());
        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(order, orderByPath);

        int offset = (param.getPageNumber() - 1) * param.getPageSize();
        return jpaQueryFactory.selectFrom(product)
                .where(namePredicate, categoryPredicate)
                .offset(offset)
                .limit(param.getPageSize())
                .orderBy(orderSpecifier)
                .fetch();
    }

    @Override
    public Product createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        product.setDescription(productRequest.getDescription());
        Date now = new Date();
        product.setCreatedDate(now);
        product.setLastModifiedDate(now);
        productRepository.save(product);

        // 將庫存寫入Redis
        String redisKey = STOCK_KEY_PREFIX + product.getId();
        redisTemplate.opsForValue().set(redisKey, String.valueOf(product.getStock()));

        return product;
    }

    @Override
    public long getProductsCount(ProductQueryParam param) {
        QProduct product = QProduct.product;
        BooleanExpression namePredicate = (!"".equals(param.getSearch())) ? product.productName.containsIgnoreCase(param.getSearch()) : null;
        BooleanExpression categoryPredicate = (!"".equals(param.getCategory())) ? product.category.eq(param.getCategory()) : null;
        Long count = jpaQueryFactory.select(product.count())
                .from(product)
                .where(namePredicate, categoryPredicate)
                .fetchFirst();
        return (count != null)? count : 0L;
    }

    @Override
    public Integer getProductStockById(Long productId) {
        String redisKey = STOCK_KEY_PREFIX + productId;
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        //先從Redis查詢庫存
        String stockStr = valueOperations.get(redisKey);
        if (stockStr != null) {
            return Integer.parseInt(stockStr);
        }

        Integer stock = productRepository.findById(productId)
                .map(Product::getStock)
                .orElseThrow(() -> new RuntimeException("未找到該商品"));

        valueOperations.set(redisKey, String.valueOf(stock));

        return stock;
    }

    @Override
    public Product updateProduct(Long productId, ProductRequest productRequest) {
        Product existProduct = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "該筆產品不存在，請重新查詢"));
        existProduct.setCategory(productRequest.getCategory());
        existProduct.setProductName(productRequest.getProductName());
        existProduct.setPrice(productRequest.getPrice());
        existProduct.setStock(productRequest.getStock());
        existProduct.setImageUrl(productRequest.getImageUrl());
        existProduct.setDescription(productRequest.getDescription());
        productRepository.save(existProduct);

        // 更新Redis中的庫存
        String redisKey = STOCK_KEY_PREFIX + existProduct.getId();
        redisTemplate.opsForValue().set(redisKey, String.valueOf(existProduct.getStock()));

        return existProduct;
    }
}
