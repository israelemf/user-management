package com.israelemf.userManagementSystem.services;

import com.israelemf.userManagementSystem.dtos.user.UserPutDto;
import com.israelemf.userManagementSystem.dtos.user.UserResponseDto;
import com.israelemf.userManagementSystem.entities.User;
import com.israelemf.userManagementSystem.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        String login = user.getLogin();

        if (this.userRepository.findUserByLogin(login).isPresent()) {
            throw new EntityExistsException("Login " + login + " already exists!");
        }

        String encryptedPassword = this.bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        user = this.userRepository.save(user);

        return user;
    }

    @Transactional
    public User update(String login, UserPutDto userPutDto) {
        User userToUpdate = this.userRepository.findUserByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        BeanUtils.copyProperties(userPutDto, userToUpdate, getNullPropertyNames(userPutDto));

        return this.userRepository.save(userToUpdate);
    }

    @Transactional
    public String deleteByLogin(String login) {
        User userToDelete = this.userRepository.findUserByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        this.userRepository.deleteByLogin(userToDelete.getLogin());

        return "User " + userToDelete.getLogin() + " deleted!";
    }

    public String deleteAll() {
        try {
            this.userRepository.deleteAll();
        } catch (Exception exception) {
            return "Erro ao excluir todos os usuários";
        }

        return "Usuários deletados!";
    }

    // Função para obter nomes de propriedades nulas em um objeto
    private String[] getNullPropertyNames(Object json) {
        final BeanWrapper jsonData = new BeanWrapperImpl(json);
        Set<String> NullPropertiesNames = new HashSet<>();

        for (PropertyDescriptor propertyDescriptor : jsonData.getPropertyDescriptors()) {
            if (jsonData.getPropertyValue(propertyDescriptor.getName()) == null) {
                NullPropertiesNames.add(propertyDescriptor.getName());
            }
        }
        return NullPropertiesNames.toArray(new String[0]);
    }
}
