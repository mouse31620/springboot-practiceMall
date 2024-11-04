package com.example.springbootpracticemall.service.impl;

import com.example.springbootpracticemall.model.dto.OrderDto;
import com.example.springbootpracticemall.model.dto.OrderProductResponse;
import com.example.springbootpracticemall.model.dto.OrderQueryParam;
import com.example.springbootpracticemall.model.dto.OrderRequest;
import com.example.springbootpracticemall.model.entity.*;
import com.example.springbootpracticemall.parameter.OrderState;
import com.example.springbootpracticemall.repository.OrderRepository;
import com.example.springbootpracticemall.repository.ProductRepository;
import com.example.springbootpracticemall.service.OrderService;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        Order newOrder = new Order();
        User orderUser = new User();
        orderUser.setId(orderRequest.getOrderUserId());
        newOrder.setOrderUser(orderUser);
        newOrder.setReceiverName(orderRequest.getReceiverName());
        newOrder.setReceiverEmail(orderRequest.getReceiverEmail());
        newOrder.setReceiverAddress(orderRequest.getReceiverAddress());
        newOrder.setOrderPrice(orderRequest.getOrderPrice());

        orderRequest.getProducts().forEach(orderProductDto -> {
            Product product = productRepository.findByIdWithLock(orderProductDto.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "無法找到 ID 為 " + orderProductDto.getProductId() + " 的產品"));

            Integer productStock = product.getStock();
            System.out.println("測試 : " + productStock);
            if (productStock < orderProductDto.getQuantity()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "商品[" + product.getProductName() +"]庫存不足，請重新選購商品");
            }

            newOrder.addProduct(product, orderProductDto.getQuantity());
            product.setStock(productStock - orderProductDto.getQuantity());
            productRepository.save(product);
        });

        newOrder.setOrderState(OrderState.PENDING.name());
        Date now = new Date();
        newOrder.setCreatedDate(now);
        newOrder.setLastModifiedDate(now);
        return orderRepository.save(newOrder);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "未找到該筆訂單"));
    }

    @Override
    public Order updateOrder(Long orderId, OrderRequest orderRequest) {
        return null;
    }

    @Override
    public long getOrderCount(OrderQueryParam orderQueryParam) {
        QOrder qOrder = QOrder.order;
        BooleanExpression orderIdPredicate = orderQueryParam.getOrderId() > 0 ? qOrder.id.eq(orderQueryParam.getOrderId()) : null;
        BooleanExpression userIdPredicate = orderQueryParam.getUserId() > 0 ? qOrder.orderUser.id.eq(orderQueryParam.getUserId()) : null;
        BooleanExpression searchStartDatePredicate = orderQueryParam.getSearchStartDate() != null ? qOrder.createdDate.after(orderQueryParam.getSearchStartDate()) : null;
        BooleanExpression searchEndDatePredicate = orderQueryParam.getSearchEndDate() != null ? qOrder.createdDate.before(orderQueryParam.getSearchEndDate()) : null;
        BooleanExpression orderStatePredicate = !"".equals(orderQueryParam.getOrderState()) ? qOrder.orderState.eq(orderQueryParam.getOrderState()) : null;
        Long count = jpaQueryFactory.select(qOrder.count())
                .from(qOrder)
                .where(orderIdPredicate, userIdPredicate, searchStartDatePredicate, searchEndDatePredicate, orderStatePredicate)
                .fetchFirst();
        return count != null ? count : 0;
    }

    @Override
    public List<OrderDto> getOrders(OrderQueryParam orderQueryParam) {
        QOrder qOrder = QOrder.order;
        BooleanExpression orderIdPredicate = orderQueryParam.getOrderId() > 0 ? qOrder.id.eq(orderQueryParam.getOrderId()) : null;
        BooleanExpression userIdPredicate = orderQueryParam.getUserId() > 0 ? qOrder.orderUser.id.eq(orderQueryParam.getUserId()) : null;
        BooleanExpression searchStartDatePredicate = orderQueryParam.getSearchStartDate() != null ? qOrder.createdDate.after(orderQueryParam.getSearchStartDate()) : null;
        BooleanExpression searchEndDatePredicate = orderQueryParam.getSearchEndDate() != null ? qOrder.createdDate.before(orderQueryParam.getSearchEndDate()) : null;
        BooleanExpression orderStatePredicate = !"".equals(orderQueryParam.getOrderState()) ? qOrder.orderState.eq(orderQueryParam.getOrderState()) : null;
        com.querydsl.core.types.Order order = orderQueryParam.getSort().equalsIgnoreCase("asc") ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC;
        Path<String> orderByPath  =  Expressions.stringPath(qOrder, orderQueryParam.getOrderBy());
        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(order, orderByPath);
        int offset = (orderQueryParam.getPageNumber() - 1) * orderQueryParam.getPageSize();
        return jpaQueryFactory.select(Projections.constructor(OrderDto.class,
                        Expressions.stringTemplate("CAST({0} AS string)", qOrder.id),
                        qOrder.orderUser.userName, qOrder.receiverName,
                        Expressions.stringTemplate("CAST({0} AS string)", qOrder.orderPrice),
                        qOrder.createdDate, qOrder.orderState))
                .from(qOrder)
                .where(orderIdPredicate, userIdPredicate, searchStartDatePredicate, searchEndDatePredicate, orderStatePredicate)
                .offset(offset)
                .limit(orderQueryParam.getPageSize())
                .orderBy(orderSpecifier)
                .fetch();
    }

    @Override
    public List<OrderProductResponse> getOrderProducts(Long orderId) {
        QOrder qOrder = QOrder.order;
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;
        QProduct qProduct = QProduct.product;

        return jpaQueryFactory.select(Projections.constructor(OrderProductResponse.class,
                qProduct.productName,
                Expressions.stringTemplate("CAST({0} AS string)", qOrderProduct.quantity),
                Expressions.stringTemplate("CAST({0} * {1} AS string)", qProduct.price, qOrderProduct.quantity)))
                .from(qOrder)
                .leftJoin(qOrderProduct).on(qOrder.eq(qOrderProduct.order))
                .leftJoin(qProduct).on(qProduct.eq(qOrderProduct.product))
                .where(qOrder.id.eq(orderId))
                .fetch();

    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findByIdWithLock(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "無法查到訂單編號為" + orderId + "的訂單"));
        order.getOrderProducts().forEach(orderProduct -> {
            Product product = productRepository.findByIdWithLock(orderProduct.getProduct().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "產品未找到"));
            int restoredStock = product.getStock() + orderProduct.getQuantity();
            product.setStock(restoredStock);
            productRepository.save(product);
        });
        orderRepository.delete(order);
    }
}
