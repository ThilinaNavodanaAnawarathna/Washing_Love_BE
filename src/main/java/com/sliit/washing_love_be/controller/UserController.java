package com.sliit.washing_love_be.controller;

import com.sliit.washing_love_be.dto.UserDto;
import com.sliit.washing_love_be.dto.VehicleDto;
import com.sliit.washing_love_be.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("all")
    public ResponseEntity<?> getAllUsers()throws Exception{
        List<UserDto> all = userService.getAll();
        if (all==null)
            return new ResponseEntity<>("Can't get users", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(all,HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getUserById(@RequestParam Long userId)throws Exception{
        Optional<UserDto> byId = userService.findById(userId);
        if (byId.get()==null)
            return new ResponseEntity<>("Can't get users", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(byId.get(),HttpStatus.OK);
    }
}
