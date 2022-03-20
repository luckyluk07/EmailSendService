package com.example.emailsendservice.Models;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDto {
    @Size(min = 6, message = "Username is too short")
    private String username;
    @Email(message = "Require email format")
    private String email;
}
