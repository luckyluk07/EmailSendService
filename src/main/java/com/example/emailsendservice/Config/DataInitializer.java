package com.example.emailsendservice.Config;

import com.example.emailsendservice.Models.UserDto;
import com.example.emailsendservice.Repositories.UserRepository;
import com.example.emailsendservice.Models.User;
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
        //L.Nojman11@wp.pl
        //lukasz.nojman07@gmail.com
        //pio_wozniak@yahoo.com
        //studniapl@gmail.com
        //first.user@gmail.com
        //second.user@gmail.com
        //third.user@gmail.com
        //fourth.user@gmail.com
        UserDto user1 = UserDto.builder()
                .username("FirstUser")
                .email("L.Nojman11@wp.pl")
                .build();

        UserDto user2 = UserDto.builder()
                .username("SecondUser")
                .email("pio_wozniak@yahoo.com")
                .build();

        UserDto user3 = UserDto.builder()
                .username("ThirdUser")
                .email("studniapl@gmail.com")
                .build();

        UserDto user4 = UserDto.builder()
                .username("FourthUser")
                .email("lukasz.nojman07@gmail.com")
                .build();

        this.service.create(user1);
        this.service.create(user2);
        this.service.create(user3);
        this.service.create(user4);
    }
}
