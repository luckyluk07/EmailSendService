package com.example.emailsendservice.Models;

import lombok.*;

import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MailMessage {
    @Size(min = 4, message = "Subject is too short")
    private String subject;
    @Size(min = 4, message = "Content is too short")
    private String mainContent;
}
