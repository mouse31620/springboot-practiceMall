package com.example.springbootpracticemall.repository;

import com.example.springbootpracticemall.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, QuerydslPredicateExecutor<Role> {

    Optional<Role> findByRoleName(String roleName);
}
