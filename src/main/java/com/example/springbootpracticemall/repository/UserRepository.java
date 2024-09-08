package com.example.springbootpracticemall.repository;

import com.example.springbootpracticemall.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findUserByEmail (String email);


}
