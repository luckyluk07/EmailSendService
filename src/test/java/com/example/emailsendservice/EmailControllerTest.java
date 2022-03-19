package com.example.emailsendservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class EmailControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper mapper;
    private User user1, user2, user3;
    @Autowired
    public EmailControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.mapper = objectMapper;
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
    }

    @MockBean
    UserRepository userRepository;

    @Test
    public void getUserById_success() throws Exception {
        Mockito.when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/emails/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

}
