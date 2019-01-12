package com.ck.customcrud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.ck.customcrud.mapper")
public class CustomCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomCrudApplication.class, args);
    }

}


