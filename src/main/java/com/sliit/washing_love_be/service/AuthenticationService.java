package com.sliit.washing_love_be.service;

import com.sliit.washing_love_be.dto.UserDto;

public interface AuthenticationService {
    UserDto signInAndReturnJWT(UserDto signInRequest);
}
