package com.sliit.washing_love_be.service.impl;

import com.sliit.washing_love_be.dto.UserDto;
import com.sliit.washing_love_be.entity.User;
import com.sliit.washing_love_be.repository.UserRepository;
import com.sliit.washing_love_be.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto save(UserDto user) throws Exception {
        try {
            User userModel = modelMapper.map(user, User.class);
            userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
            userModel.setCreateTime(LocalDateTime.now());
            userModel.setRole(user.getRole());
            User save = userRepository.save(userModel);
            return modelMapper.map(save, UserDto.class);
        } catch (Exception e) {
            log.error("Error user create : Error: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        Optional<User> byUsername = userRepository.findByEmail(email);

        return byUsername.map(user -> UserDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createTime(user.getCreateTime())
                .role(user.getRole())
                .build());
    }
}
