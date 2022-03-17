package com.example.emailsendservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataInitializer {
    private UserRepository repository;

    @Autowired
    public DataInitializer(UserRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void initUsers() {
        User user1 = User.builder()
                .username("FirstUser")
                .email("first.user@gmail.com")
                .build();

        this.repository.save(user1);
    }
}
