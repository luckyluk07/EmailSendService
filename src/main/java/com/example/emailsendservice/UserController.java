package com.example.emailsendservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return this.userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = this.userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody User user, UriComponentsBuilder builder) {
        User createdUser = this.userService.create(user);
        return ResponseEntity
                .created(builder.pathSegment("api", "emails", "{id}")
                        .buildAndExpand(createdUser.getId())
                        .toUri())
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateById(@PathVariable Long id, @RequestBody User updatedUser) {
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
