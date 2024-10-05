package com.example.springbootpracticemall.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "privilege")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "privilege_name", nullable = false, unique = true)
    String privilegeName;
    @Column(name = "privilege_chinese")
    String privilegeChinese;
    @ManyToMany(mappedBy = "privileges", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Role> roles = new HashSet<>();
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;
}
