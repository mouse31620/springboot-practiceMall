package com.example.springbootpracticemall.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "customer_type")
public class CustomerType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "type_name", nullable = false, unique = true)
    private String typeName;
    @Column(name = "type_chinese")
    private String typeChinese;
    @OneToMany(mappedBy = "customerType")
    private Set<User> users = new HashSet<>();
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;
}
