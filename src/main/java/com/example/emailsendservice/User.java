package com.example.emailsendservice;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {
    private long id;
    private String username;
    private String email;
}
