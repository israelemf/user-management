package com.israelemf.userManagementSystem.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserPutDto( String login,
                          @Email String email,
                          String password) {

}
