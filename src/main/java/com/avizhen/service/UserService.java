package com.avizhen.service;

import com.avizhen.dto.UserRegistrationDto;
import com.avizhen.entity.User;
import com.avizhen.enums.Role;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<User> findAllUsers(int pageNumber, String username,  String firstName);

    Page<User> findAllUsers(int pageNumber);

    User findUserById(Integer id);

    User findUserByUsername(String username);

    User registerUser(UserRegistrationDto userRegistrationDto);

    void updateUser(Integer userId, UserRegistrationDto userRegistrationDto);

    void delete(Integer userId);

    void lockUser(Integer userId);

}
