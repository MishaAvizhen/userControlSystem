package com.avizhen.service;

import com.avizhen.dto.UserRegistrationDto;
import com.avizhen.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();

    User findUserById(Integer id);

    User registerUser(UserRegistrationDto userRegistrationDto);
}
