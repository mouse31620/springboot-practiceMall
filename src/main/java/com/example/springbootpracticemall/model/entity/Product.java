package com.example.springbootpracticemall.model.entity;

import com.example.springbootpracticemall.parameter.ProductCategory;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "category")
    private ProductCategory category;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "price")
    private Integer price;
    @Column(name = "stock")
    private Integer stock;
    @Column(name = "description")
    private String description;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;


}
