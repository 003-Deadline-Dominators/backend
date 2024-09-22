package com.parsons;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.parsons")
@MapperScan("com.parsons.mapper")
public class ParsonsApp {
    public static void main(String[] args) {
        SpringApplication.run(ParsonsApp.class, args);
    }
}
