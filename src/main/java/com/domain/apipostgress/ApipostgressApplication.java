package com.domain.apipostgress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ApipostgressApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApipostgressApplication.class, args);
    }
}
