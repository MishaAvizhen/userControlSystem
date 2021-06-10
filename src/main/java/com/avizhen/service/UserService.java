package com.avizhen.service;

import com.avizhen.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();

    User findUserById(Integer id);
}
