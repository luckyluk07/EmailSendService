package com.example.emailsendservice;

import com.example.emailsendservice.Controllers.UserController;
import com.example.emailsendservice.Models.User;
import com.example.emailsendservice.Services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    private List<User> users;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void init() {
        this.users = new ArrayList<>();
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

        this.users.add(user1);
        this.users.add(user2);
        this.users.add(user3);
    }

    @AfterEach
    public void tearDown() {
        user1 = user2 = user3 = null;
        users = null;
    }

    @Test
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
            throws Exception {

        Mockito.when(service.findAll()).thenReturn(users);

        mvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void findById_UsersExist_ReturnOkAndUser() throws Exception {
        Mockito.when(service.findById(user1.getId())).thenReturn(user1);

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/users/1/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
