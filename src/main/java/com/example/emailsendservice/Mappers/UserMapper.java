package com.example.emailsendservice.Mappers;

import com.example.emailsendservice.Models.User;
import com.example.emailsendservice.Models.UserDto;

public class UserMapper {
    public static UserDto userModelToUserDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public static User dtoToUserModel(UserDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .build();
    }
}
