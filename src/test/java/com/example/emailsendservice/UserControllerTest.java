package com.example.emailsendservice;

import com.example.emailsendservice.Controllers.UserController;
import com.example.emailsendservice.Models.User;
import com.example.emailsendservice.Services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;
    private User user1;
    private User user2;
    private User user3;
    private List<User> users;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

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
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @AfterEach
    public void tearDown() {
        user1 = user2 = user3 = null;
        users = null;
    }


}