package com.sparta.spartaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpartaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpartaApiApplication.class, args);
        System.out.println("Sparta API started successfully");
    }
}
