package com.example.emailsendservice.Config;

import com.example.emailsendservice.Repositories.UserRepository;
import com.example.emailsendservice.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
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
                .email("L.Nojman11@wp.pl")
                .build();

        User user2 = User.builder()
                .username("SecondUser")
                .email("second.user@gmail.com")
                .build();

        User user3 = User.builder()
                .username("ThirdUser")
                .email("third.user@gmail.com")
                .build();

        this.repository.save(user1);
        this.repository.save(user2);
        this.repository.save(user3);
    }
}
