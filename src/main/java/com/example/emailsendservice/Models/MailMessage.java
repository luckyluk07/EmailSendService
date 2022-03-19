package com.example.emailsendservice.Models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MailMessage {
    private String subject;
    private String mainContent;
}
