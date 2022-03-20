package com.example.emailsendservice;

import com.example.emailsendservice.Models.EmailDto;
import com.example.emailsendservice.Models.User;
import com.example.emailsendservice.Repositories.UserRepository;
import com.example.emailsendservice.Services.EmailService;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private EmailService emailService;
    private User user1;
    private User user2;
    private User user3;
    private List<EmailDto> emails;
    private List<User> users;

    @BeforeEach
    public void init() {
        this.emails = new ArrayList<>();
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

        this.emails.add(new EmailDto(user1.getEmail()));
        this.emails.add(new EmailDto(user2.getEmail()));
        this.emails.add(new EmailDto(user3.getEmail()));
    }

    @AfterEach
    public void tearDown() {
        user1 = user2 = user3 = null;
        emails = null;
    }

    @Test
    void findAll_3ProperUsers_returnAllEmails() {
        Mockito.when(userRepository.findAll()).thenReturn(users);

        List<EmailDto> serviceEmails = this.emailService.findAll();

        Assertions.assertEquals(emails, serviceEmails);
        Mockito.verify(userRepository, times(1)).findAll();
    }

    @Test
    void findById_noEmailBelongsToUserWithThatId_returnNull() {
        Optional<User> user = Optional.empty();
        Mockito.when(userRepository.findById(anyLong())).thenReturn(user);

        EmailDto fetchedEmail = this.emailService.findById(1L);

        Assertions.assertNull(fetchedEmail);
        Mockito.verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void findById_emailBelongsToUserWithThatId_returnThisUser() {
        Optional<User> user = Optional.ofNullable(user1);
        Mockito.when(userRepository.findById(anyLong())).thenReturn(user);

        EmailDto fetchedEmail = this.emailService.findById(1L);

        Assertions.assertEquals(user1.getEmail(), fetchedEmail.getEmail());
        Mockito.verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void updateById_emailBelongsToExistingUser_serviceCallRepositoryMethod() {
        Mockito.doNothing().when(userRepository).updateEmail(anyLong(), anyString());

        this.emailService.updateEmail(user1.getId(), EmailDto.builder().email(user1.getEmail()).build());

        Mockito.verify(userRepository, times(1)).updateEmail(anyLong(), anyString());
    }
}
