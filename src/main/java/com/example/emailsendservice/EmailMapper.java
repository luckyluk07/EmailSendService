package com.example.emailsendservice;

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
