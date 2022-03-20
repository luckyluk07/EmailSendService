package com.example.emailsendservice;

import com.example.emailsendservice.Controllers.UserController;
import com.example.emailsendservice.Mappers.JsonMapper;
import com.example.emailsendservice.Models.User;
import com.example.emailsendservice.Services.UserService;
import org.hamcrest.CoreMatchers;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

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
    public void findAll_3ProperUsers_returnOkCodeAndJsonArray()
            throws Exception {

        Mockito.when(userService.findAll()).thenReturn(users);

        mvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        Mockito.verify(userService,times(1)).findAll();
    }

    @Test
    public void findById_userExist_returnOkAndUser() throws Exception {
        Mockito.when(userService.findById(user1.getId())).thenReturn(user1);

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/users/1/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(user1.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is((int)user1.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(user1.getEmail())));

        Mockito.verify(userService,times(1)).findById(user1.getId());
    }

    @Test
    public void findById_userNotExist_returnNotFound() throws Exception {
        Mockito.when(userService.findById(anyLong())).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/users/4/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(userService,times(1)).findById(anyLong());
    }

    @Test
    public void create_properUser_returnCreated() throws Exception {
        User user = User.builder().username("user").email("gmail.com").build();
        Mockito.when(userService.create(any())).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(user)))
                .andExpect(status().isCreated());

        Mockito.verify(userService,times(1)).create(any());
    }
}
