package com.avizhen.service;

import com.avizhen.dto.UserRegistrationDto;
import com.avizhen.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();

    User findUserById(Integer id);

    User findUserByUsername(String username);

    User registerUser(UserRegistrationDto userRegistrationDto);

    User updateUser(Integer userId, UserRegistrationDto userRegistrationDto);

    void delete(Integer userId);
}
