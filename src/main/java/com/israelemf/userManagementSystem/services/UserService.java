package com.israelemf.userManagementSystem.services;

import com.israelemf.userManagementSystem.dtos.user.UserPutDto;
import com.israelemf.userManagementSystem.dtos.user.UserResponseDto;
import com.israelemf.userManagementSystem.entities.User;
import com.israelemf.userManagementSystem.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<UserResponseDto> listAllUsers() {
        return this.userRepository.findAllUserResponseDto();
    }

    public User save(User user) {
        String encryptedPassword = this.bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        user = this.userRepository.save(user);

        return user;
    }

    @Transactional
    public User update(String login, UserPutDto userPutDto) {
        User userToUpdate = this.userRepository.findUserByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(login + " não encontrado!"));

        BeanUtils.copyProperties(userPutDto, userToUpdate);

        return this.userRepository.save(userToUpdate);
    }

    @Transactional
    public void deleteByLogin(String login) {
       this.userRepository.deleteByLogin(login);
    }

    public String deleteAll() {
        try {
            this.userRepository.deleteAll();
        } catch (Exception exception) {
            return "Erro ao excluir todos os usuários";
        }

        return "Usuários deletados!";
    }
}
