package com.example.emailsendservice.Models;

import lombok.*;

import javax.validation.constraints.Email;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class EmailDto {
    @Email(message = "Require email format")
    private String email;
}
