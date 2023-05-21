package com.sliit.washing_love_be.service;

import com.sliit.washing_love_be.dto.UserDto;

import java.util.Optional;

public interface UserService {
    UserDto save(UserDto user) throws Exception;

    Optional<UserDto> findByEmail(String email);
}
