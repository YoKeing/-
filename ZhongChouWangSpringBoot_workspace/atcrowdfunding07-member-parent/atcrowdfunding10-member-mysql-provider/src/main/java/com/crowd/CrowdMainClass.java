package com.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;


@MapperScan("com.crowd.mapper")
@SpringBootApplication
public class CrowdMainClass {

    public static void main(String[] args) {




        SpringApplication.run(CrowdMainClass.class, args);
    }

}
