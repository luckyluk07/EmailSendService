package com.example.emailsendservice.Models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class EmailDto {
    private String email;
}
