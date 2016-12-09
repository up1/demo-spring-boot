package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JDBCApplication {
    public static void main(String[] args) {
        SpringApplication.run(JDBCApplication.class, args).close();

        System.out.println("Let's inspect the beans provided by Spring Boot:");

    }
}
