package com.israelemf.userManagementSystem.controllers;

import com.israelemf.userManagementSystem.dtos.user.UserRequestDto;
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
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        var user = new User();
        // Conversão de DTO para User
        BeanUtils.copyProperties(userRequestDto, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @DeleteMapping("/users/{login}")
    public ResponseEntity<String> deleteUserByName(@PathVariable("login") String login) {
        this.userService.deleteByLogin(login);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário " + login + " excluído com suceso!");
    }
}
