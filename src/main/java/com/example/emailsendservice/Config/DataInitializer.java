package com.example.emailsendservice.Config;

import com.example.emailsendservice.Models.UserDto;
import com.example.emailsendservice.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataInitializer {
    private UserService service;

    @Autowired
    public DataInitializer(UserService service) {
        this.service = service;
    }

    @PostConstruct
    public void initUsers() {
        UserDto user1 = UserDto.builder()
                .username("FirstUser")
                .email("L.Nojman11@wp.pl")
                .build();

        UserDto user2 = UserDto.builder()
                .username("SecondUser")
                .email("lukasz.nojman07@gmail.com")
                .build();

        UserDto user3 = UserDto.builder()
                .username("ThirdUser")
                .email("third.user@gmail.com")
                .build();

        UserDto user4 = UserDto.builder()
                .username("FourthUser")
                .email("fourth.user@gmail.com")
                .build();

        this.service.create(user1);
        this.service.create(user2);
        this.service.create(user3);
        this.service.create(user4);
    }
}
