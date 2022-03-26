package com.example.emailsendservice;

import com.example.emailsendservice.Controllers.EmailSenderController;
import com.example.emailsendservice.Mappers.JsonMapper;
import com.example.emailsendservice.Models.MailMessage;
import com.example.emailsendservice.Services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmailSenderController.class)
public class EmailSenderControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmailSenderService emailSenderService;

    @Test
    public void sendEmail_userExists_returnOk() throws Exception {
        Mockito.when(emailSenderService.sendSimpleMessage(anyLong(), any())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/sendmail/1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(MailMessage.builder()
                                .subject("subject")
                                .mainContent("content")
                                .build())))
                .andExpect(status().isOk());

        Mockito.verify(emailSenderService, times(1)).sendSimpleMessage(anyLong(), any());
    }

    @Test
    public void sendEmail_userNotExists_returnNotFound() throws Exception {
        Mockito.when(emailSenderService.sendSimpleMessage(anyLong(), any())).thenReturn(false);

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/sendmail/4/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(MailMessage.builder()
                                .subject("subject")
                                .mainContent("content")
                                .build())))
                .andExpect(status().isNotFound());

        Mockito.verify(emailSenderService, times(1)).sendSimpleMessage(anyLong(), any());
    }

    @Test
    public void sendEmailToAll_properAction_returnOkCallMethodOnce() throws Exception {
        Mockito.doNothing().when(emailSenderService).sendToAll(any());

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/sendToAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(MailMessage.builder()
                                .subject("subject")
                                .mainContent("content")
                                .build())))
                .andExpect(status().isOk());

        Mockito.verify(emailSenderService, times(1)).sendToAll(any());
    }

}
