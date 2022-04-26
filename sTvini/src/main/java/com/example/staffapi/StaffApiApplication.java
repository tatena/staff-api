package com.example.staffapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
@ConfigurationPropertiesScan
public class StaffApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaffApiApplication.class, args);
    }

}
