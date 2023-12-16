package com.israelemf.userManagementSystem.services;

import com.israelemf.userManagementSystem.entities.User;
import com.israelemf.userManagementSystem.repositories.UserRepository;
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

    public List<User> listAllUsers() {
        return this.userRepository.findAll();
    }

    public User save(User user) {
        String encryptedPassword = this.bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encryptedPassword);
        user = this.userRepository.save(user);

        return user;
    }

    public User update(User user) {
        return this.userRepository.save(user);
    }

    public User deleteByLogin(String login) {
        return this.userRepository.deleteByLogin(login);
    }

    public String deleteAllUsers() {
        try {
            this.userRepository.deleteAll();
        } catch (Exception exception) {
            return "Erro ao excluir todos os usuários";
        }

        return "Usuários deletados!";
    }
}
