package com.example.emailsendservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class EmailSendServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailSendServiceApplication.class, args);
    }

}
