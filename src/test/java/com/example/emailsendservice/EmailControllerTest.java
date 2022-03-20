package com.example.emailsendservice;

import com.example.emailsendservice.Controllers.EmailController;
import com.example.emailsendservice.Mappers.JsonMapper;
import com.example.emailsendservice.Models.EmailDto;
import com.example.emailsendservice.Models.User;
import com.example.emailsendservice.Services.EmailService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@WebMvcTest(EmailController.class)
public class EmailControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private EmailService emailService;

    private List<User> users;
    private User user1;
    private User user2;
    private User user3;
    private List<EmailDto> emails;
    private EmailDto email1;
    private EmailDto email2;
    private EmailDto email3;

    @BeforeEach
    public void init() {
        this.users = new ArrayList<>();
        this.emails = new ArrayList<>();
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

        this.email1 = new EmailDto(user1.getEmail());
        this.email2 = new EmailDto(user2.getEmail());
        this.email3 = new EmailDto(user3.getEmail());

        this.emails.add(email1);
        this.emails.add(email2);
        this.emails.add(email3);
    }

    @AfterEach
    public void tearDown() {
        email1 = email2 = email3 = null;
        user1 = user2 = user3 = null;
        users = null;
        emails = null;
    }

    @Test
    public void findAll_3ProperUsersWithEmails_returnOkCodeAndJsonArray()
            throws Exception {

        Mockito.when(emailService.findAll()).thenReturn(emails);

        mvc.perform(MockMvcRequestBuilders.get("/api/emails")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        Mockito.verify(emailService, times(1)).findAll();
    }

    @Test
    public void findById_emailBelongsToExistingUser_returnOkAndEmail() throws Exception {
        Mockito.when(emailService.findById(user1.getId())).thenReturn(EmailDto.builder().email(user1.getEmail()).build());

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/emails/1/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(user1.getEmail())));

        Mockito.verify(emailService, times(1)).findById(user1.getId());
    }

    @Test
    public void findById_emailNotBelongsToExistingUser_returnNotFound() throws Exception {
        Mockito.when(emailService.findById(anyLong())).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/emails/4/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(emailService, times(1)).findById(anyLong());
    }

    @Test
    public void updateById_userExist_returnAcceptedServiceWillUpdate() throws Exception {
        Mockito.when(userService.findById(anyLong())).thenReturn(user1);
        Mockito.doNothing().when(emailService).updateEmail(anyLong(), any());

        mvc.perform(MockMvcRequestBuilders
                        .put("/api/emails/1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(email3)))
                .andExpect(status().isAccepted());

        Mockito.verify(userService, times(1)).findById(anyLong());
        Mockito.verify(emailService, times(1)).updateEmail(anyLong(), any());
    }

    @Test
    public void updateById_userNotExist_returnNotFoundServiceWillNotUpdate() throws Exception {
        Mockito.when(userService.findById(anyLong())).thenReturn(null);
        Mockito.doNothing().when(emailService).updateEmail(anyLong(), any());

        mvc.perform(MockMvcRequestBuilders
                        .put("/api/emails/4/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(email3)))
                .andExpect(status().isNotFound());

        Mockito.verify(userService, times(1)).findById(anyLong());
        Mockito.verify(emailService, times(0)).updateEmail(anyLong(), any());
    }
}
