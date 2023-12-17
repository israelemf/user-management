package com.israelemf.userManagementSystem.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserPostDto(@NotBlank String login,
                          @NotBlank @Email String email,
                          @NotBlank String password) {
}
