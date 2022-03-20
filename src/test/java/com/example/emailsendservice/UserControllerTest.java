package com.example.emailsendservice;

import com.example.emailsendservice.Controllers.UserController;
import com.example.emailsendservice.Mappers.JsonMapper;
import com.example.emailsendservice.Mappers.UserMapper;
import com.example.emailsendservice.Models.User;
import com.example.emailsendservice.Models.UserDto;
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
        Mockito.when(userService.create(any(UserDto.class))).thenReturn(user1);

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(UserMapper.userModelToUserDto(user1))))
                .andExpect(status().isCreated());

        Mockito.verify(userService,times(1)).create(any(UserDto.class));
    }

    @Test
    public void updateById_userExist_returnAcceptedServiceWillUpdate() throws Exception {
        Mockito.when(userService.findById(anyLong())).thenReturn(user1);
        Mockito.doNothing().when(userService).updateById(anyLong(),any());

        mvc.perform(MockMvcRequestBuilders
                        .put("/api/users/1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(user1)))
                .andExpect(status().isAccepted());

        Mockito.verify(userService,times(1)).findById(anyLong());
        Mockito.verify(userService,times(1)).updateById(anyLong(), any());
    }

    @Test
    public void updateById_userNotExist_returnNotFoundServiceWillNotUpdate() throws Exception {
        Mockito.when(userService.findById(anyLong())).thenReturn(null);
        Mockito.doNothing().when(userService).updateById(anyLong(),any());

        mvc.perform(MockMvcRequestBuilders
                        .put("/api/users/4/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(user1)))
                .andExpect(status().isNotFound());

        Mockito.verify(userService,times(1)).findById(anyLong());
        Mockito.verify(userService,times(0)).updateById(anyLong(), any());
    }

    @Test
    public void deleteById_userExist_returnAcceptedServiceWillUpdate() throws Exception {
        Mockito.when(userService.findById(anyLong())).thenReturn(user1);
        Mockito.doNothing().when(userService).delete(any());

        mvc.perform(MockMvcRequestBuilders
                        .delete("/api/users/1/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        Mockito.verify(userService,times(1)).findById(anyLong());
        Mockito.verify(userService,times(1)).delete(any());
    }

    @Test
    public void deleteById_userNotExist_returnNotFoundServiceWillNotUpdate() throws Exception {
        Mockito.when(userService.findById(anyLong())).thenReturn(null);
        Mockito.doNothing().when(userService).delete(any());

        mvc.perform(MockMvcRequestBuilders
                        .delete("/api/users/4/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(userService,times(1)).findById(anyLong());
        Mockito.verify(userService,times(0)).delete(any());
    }
}
