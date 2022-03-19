package com.example.emailsendservice;

import com.example.emailsendservice.Models.User;
import com.example.emailsendservice.Repositories.UserRepository;
import com.example.emailsendservice.Services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserService userService;
    private User user1;
    private User user2;
    private User user3;
    private List<User> users;

    @BeforeEach
    public void init() {
        users = new ArrayList<>();
        User user1 = User.builder()
                .id(1)
                .username("FirstUser")
                .email("first.user@gmail.com")
                .build();

        User user2 = User.builder()
                .id(2)
                .username("SecondUser")
                .email("second.user@gmail.com")
                .build();

        User user3 = User.builder()
                .id(3)
                .username("ThirdUser")
                .email("third.user@gmail.com")
                .build();

        users.add(user1);
        users.add(user2);
        users.add(user3);
    }

    @AfterEach
    public void tearDown() {
        user1 = user2 = user3 = null;
        users = null;
    }

    @Test
    void givenUserToAddShouldReturnAddedUser(){
        Mockito.when(userRepository.save(any())).thenReturn(user1);
        this.userService.create(user1);

        Mockito.verify(userRepository,times(1)).save(user1);
    }

    @Test
    void givenGetAllUsersShouldReturnAllUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(users);
        List<User> serviceUsers = this.userService.findAll();
        Assertions.assertEquals(users, serviceUsers);
        Mockito.verify(userRepository,times(1)).findAll();
    }

}
