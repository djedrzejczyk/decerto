package com.github.djedrzejczyk.decerto.numbers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@ConfigurationPropertiesScan
@SpringBootApplication
@EnableFeignClients
public class NumbersApplication {

    public static void main(String[] args) {
        SpringApplication.run(NumbersApplication.class, args);
    }

}
