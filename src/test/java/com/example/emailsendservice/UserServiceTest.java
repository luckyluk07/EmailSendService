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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
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
        this.user1 = User.builder()
                .id(1)
                .username("FirstUser")
                .email("first.user@gmail.com")
                .build();

        this.user2 = User.builder()
                .id(2)
                .username("SecondUser")
                .email("second.user@gmail.com")
                .build();

        this.user3 = User.builder()
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
    void create_addProperUser_returnAddedUser(){
        Mockito.when(userRepository.save(any())).thenReturn(user1);
        User user = this.userService.create(user1);

        Assertions.assertEquals(user1, user);
        Mockito.verify(userRepository,times(1)).save(user1);
    }

    @Test
    void findAll_3ProperUsers_returnAllUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(users);
        List<User> serviceUsers = this.userService.findAll();

        Assertions.assertEquals(users, serviceUsers);
        Mockito.verify(userRepository,times(1)).findAll();
    }

    @Test
    void findById_noUserWithThatId_returnNull() {
        Optional<User> user = Optional.empty();
        Mockito.when(userRepository.findById(anyLong())).thenReturn(user);

        User fetchedUser = this.userService.findById(1L);

        Assertions.assertNull(fetchedUser);
        Mockito.verify(userRepository,times(1)).findById(anyLong());
    }

    @Test
    void findById_userWithThatIdExist_returnThisUser() {
        Optional<User> user = Optional.ofNullable(user1);
        Mockito.when(userRepository.findById(anyLong())).thenReturn(user);

        User fetchedUser = this.userService.findById(1L);

        Assertions.assertEquals(user1, fetchedUser);
        Mockito.verify(userRepository,times(1)).findById(anyLong());
    }

    @Test
    void delete_existingUser_serviceCallRepositoryMethod() {
        Mockito.doNothing().when(userRepository).delete(any());

        this.userService.delete(user1);

        Mockito.verify(userRepository, times(1)).delete(any());
    }

    @Test
    void updateById_existingUser_serviceCallRepositoryMethod() {
        Mockito.doNothing().when(userRepository).updateUser(anyLong(),anyString(),anyString());

        this.userService.updateById(user1.getId(), user1);

        Mockito.verify(userRepository, times(1)).updateUser(anyLong(),anyString(),anyString());
    }

}
