package com.example.emailsendservice.Controllers;

import com.example.emailsendservice.Models.User;
import com.example.emailsendservice.Models.UserDto;
import com.example.emailsendservice.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {
    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        this.modelMapper = new ModelMapper();
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(this.userService.findAll()
                .stream()
                .map(x -> modelMapper.map(x, UserDto.class))
                .collect(Collectors
                        .toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        User user = this.userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modelMapper.map(user, UserDto.class));
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody UserDto userDto, UriComponentsBuilder builder) {
        User createdUser = this.userService.create(userDto);
        return ResponseEntity
                .created(builder.pathSegment("api", "emails", "{id}")
                        .buildAndExpand(createdUser.getId())
                        .toUri())
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateById(@PathVariable Long id, @Valid @RequestBody UserDto updatedUser) {
        User user = this.userService.findById(id);
        if (user != null) {
            this.userService.updateById(id, updatedUser);
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        User user = this.userService.findById(id);
        if (user != null) {
            this.userService.delete(user);
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.notFound().build();
    }
}
