package com.example.emailsendservice;

import com.example.emailsendservice.Controllers.UserController;
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
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @Test
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
            throws Exception {

        User user = User.builder().id(1L).username("firstName").email("First.user@gmail.com").build();

        List<User> allUsers = Arrays.asList(user);

        Mockito.when(service.findAll()).thenReturn(allUsers);
        //given(service.getAllEmployees()).willReturn(allEmployees);

        mvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
                //.andExpect(jsonPath("$[0].name", is(alex.getName())));
    }

    @Test
    public void findById_UsersExist_ReturnOkAndUser() throws Exception {
        User user = User.builder().id(1L).username("firstName").email("First.user@gmail.com").build();
        Mockito.when(service.findById(user.getId())).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/users/1/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
