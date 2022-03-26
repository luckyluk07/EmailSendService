package com.example.emailsendservice;

import com.example.emailsendservice.Models.EmailDto;
import com.example.emailsendservice.Models.User;
import com.example.emailsendservice.Models.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

public class ModelMapperBeanTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void map_takeUser_returnUserDto() {
        User user = new User(1L, "name", "name1@gmail.com");

        UserDto dto = modelMapper.map(user, UserDto.class);

        Assertions.assertEquals(user.getEmail(), dto.getEmail());
        Assertions.assertEquals(user.getUsername(), dto.getUsername());
    }

    @Test
    public void map_takeUserDto_returnUser() {
        UserDto dto = new UserDto("name", "name1@gmail.com");

        User user = modelMapper.map(dto, User.class);

        Assertions.assertEquals(dto.getEmail(), user.getEmail());
        Assertions.assertEquals(dto.getUsername(), user.getUsername());
        Assertions.assertEquals(0,user.getId());
    }

    @Test
    public void map_takeUser_returnEmailDto() {
        User user = new User(1L, "name", "name1@gmail.com");

        EmailDto dto = modelMapper.map(user, EmailDto.class);

        Assertions.assertEquals(user.getEmail(), dto.getEmail());
    }
}
