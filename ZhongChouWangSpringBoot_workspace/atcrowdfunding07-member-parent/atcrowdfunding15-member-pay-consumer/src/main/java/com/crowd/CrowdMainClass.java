package com.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.HashSet;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class CrowdMainClass {

    public static void main(String[] args) {

        new HashSet<>().add("1");
        SpringApplication.run(CrowdMainClass.class, args);
    }

}