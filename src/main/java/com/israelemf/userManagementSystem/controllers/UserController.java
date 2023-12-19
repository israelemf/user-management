package com.israelemf.userManagementSystem.controllers;

import com.israelemf.userManagementSystem.dtos.user.UserPostDto;
import com.israelemf.userManagementSystem.dtos.user.UserPutDto;
import com.israelemf.userManagementSystem.dtos.user.UserResponseDto;
import com.israelemf.userManagementSystem.entities.User;
import com.israelemf.userManagementSystem.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.listAllUsers());
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserPostDto userPostDto) {
        var user = new User();
        BeanUtils.copyProperties(userPostDto, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/users/{login}")
    public ResponseEntity<User> updateUser(@PathVariable("login") String login, @RequestBody @Valid UserPutDto userPutDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(login, userPutDto));
    }

    @DeleteMapping("/users/{login}")
    public ResponseEntity<String> deleteUserByLogin(@PathVariable("login") String login) {
        this.userService.deleteByLogin(login);
        return ResponseEntity.status(HttpStatus.OK).body(login + " user successfully deleted!");
    }
}
