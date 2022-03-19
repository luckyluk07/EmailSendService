package com.example.emailsendservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmailService {

    private UserRepository repository;
    private EmailMapper mapper;

    @Autowired
    public EmailService(UserRepository userRepository) {
        this.repository = userRepository;
        this.mapper = new EmailMapper();
    }

    public List<EmailDto> findAll() {
        return this.repository.findAll()
                .stream()
                .map(x -> mapper.userModelToEmailDto(x))
                .collect(Collectors.toList());
    }

    public EmailDto findById(Long id) {
        Optional<User> user = this.repository.findById(id);
        if (user.isPresent()) {
            return this.mapper.userModelToEmailDto(user.get());
        }
        return null;
    }

}
