package com.israelemf.userManagementSystem.repositories;

import com.israelemf.userManagementSystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
