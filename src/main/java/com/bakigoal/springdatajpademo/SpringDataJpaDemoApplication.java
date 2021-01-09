package com.bakigoal.springdatajpademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringDataJpaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaDemoApplication.class, args);
    }

}
