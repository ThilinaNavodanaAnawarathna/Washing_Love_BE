package com.sliit.washing_love_be.service;

import com.sliit.washing_love_be.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto save(UserDto user) throws Exception;

    Optional<UserDto> findByEmail(String email);
    Optional<UserDto> findById(Long id);
    List<UserDto> getAll();
}
