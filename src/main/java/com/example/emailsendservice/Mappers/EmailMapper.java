package com.example.emailsendservice.Mappers;

import com.example.emailsendservice.Models.EmailDto;
import com.example.emailsendservice.Models.User;

public class EmailMapper {

    public EmailDto userModelToEmailDto(User user) {
        return EmailDto.builder()
                .email(user.getEmail())
                .build();
    }

    public User dtoToUserModel(EmailDto dto) {
        return User.builder()
                .email(dto.getEmail())
                .build();
    }
}
