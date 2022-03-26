package com.example.emailsendservice.Services;

import com.example.emailsendservice.Models.EmailDto;
import com.example.emailsendservice.Models.User;
import com.example.emailsendservice.Repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmailService {

    private UserRepository repository;
    private ModelMapper modelMapper;

    @Autowired
    public EmailService(UserRepository userRepository) {
        this.repository = userRepository;
        this.modelMapper = new ModelMapper();
    }

    public List<EmailDto> findAll() {
        return this.repository.findAll()
                .stream()
                .map(x -> modelMapper.map(x, EmailDto.class))
                .collect(Collectors.toList());
    }

    public EmailDto findById(Long id) {
        Optional<User> user = this.repository.findById(id);
        if (user.isPresent()) {
            EmailDto dto = modelMapper.map(user.get(), EmailDto.class);
            return dto;
        }
        return null;
    }

    @Transactional
    public void updateEmail(Long id, EmailDto dto) {
        this.repository.updateEmail(id, dto.getEmail());
    }

}
