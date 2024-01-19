package com.israelemf.userManagementSystem.dtos.user;

import java.util.UUID;

public record UserResponseDto(UUID id,
                              String login,
                              String email) {
}
