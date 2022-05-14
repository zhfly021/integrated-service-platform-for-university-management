package com.zhfly021;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.zhfly021.mapper")
@SpringBootApplication
public class GxbjglApplication {

    public static void main(String[] args) {
        SpringApplication.run(GxbjglApplication.class, args);
    }

}
