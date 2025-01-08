package com.example.springbootpracticemall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringbootPracticeMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootPracticeMallApplication.class, args);
    }

}
