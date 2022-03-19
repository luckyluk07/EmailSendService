package com.example.emailsendservice.Services;

import com.example.emailsendservice.Models.User;
import com.example.emailsendservice.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    public User create(User user) {
        return this.userRepository.save(user);
    }

    @Transactional
    public void updateById(Long id, User updatedUser) {
        this.userRepository.updateUser(id, updatedUser.getEmail(), updatedUser.getUsername());
    }

    public void delete(User user) {
        this.userRepository.delete(user);
    }
}
