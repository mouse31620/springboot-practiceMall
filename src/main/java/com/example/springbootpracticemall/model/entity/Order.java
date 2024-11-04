package com.example.springbootpracticemall.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "userOrder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User orderUser;
    @Column(name = "receiver_name")
    private String receiverName;
    @Column(name = "receiver_email")
    private String receiverEmail;
    @Column(name = "receiver_address")
    private String receiverAddress;
    @Column(name = "order_price")
    private Long orderPrice;
    @Column(name = "order_state")
    private String orderState;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    public void addProduct(Product product, Integer quantity) {
        OrderProduct orderProduct = new OrderProduct(this, product, quantity);
        orderProducts.add(orderProduct);
    }
}
