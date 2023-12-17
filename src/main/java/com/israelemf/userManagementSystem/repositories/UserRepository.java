package com.israelemf.userManagementSystem.repositories;

import com.israelemf.userManagementSystem.dtos.user.UserResponseDto;
import com.israelemf.userManagementSystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Modifying
    @Query("DELETE FROM User user WHERE user.login = :login")
    void deleteByLogin(@Param("login") String login);

    @Query("SELECT NEW com.israelemf.userManagementSystem.dtos.user.UserResponseDto(user.id, user.login, user.email) FROM User user")
    List<UserResponseDto> findAllUserResponseDto();

    @Query("SELECT user from User user where login = :login")
    Optional<User> findUserByLogin(@Param("login") String login);
}
