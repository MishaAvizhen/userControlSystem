package com.avizhen.service.impl;

import com.avizhen.converter.impl.UserConverter;
import com.avizhen.dto.UserRegistrationDto;
import com.avizhen.entity.User;
import com.avizhen.enums.Role;
import com.avizhen.enums.Status;
import com.avizhen.repository.UserRepository;
import com.avizhen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserConverter userConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userConverter = userConverter;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Integer id) {
        return userRepository.getOne(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User registerUser(UserRegistrationDto userRegistrationDto) {
        String username = userRegistrationDto.getUsername();
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            throw new RuntimeException("User with name " + username + " already exist ");
        }
        userRegistrationDto.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        User user = userConverter.convertToEntity(userRegistrationDto);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Integer userId, UserRegistrationDto userRegistrationDto) {
        User userToUpdate = userRepository.getOne(userId);
        userToUpdate.setUsername(userRegistrationDto.getUsername());
        userToUpdate.setFirstName(userRegistrationDto.getFirstName());
        userToUpdate.setLastName(userRegistrationDto.getLastName());
        userToUpdate.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        userToUpdate.setRole(userRegistrationDto.getRole());
        userToUpdate.setStatus(userRegistrationDto.getStatus());
        return userRepository.saveAndFlush(userToUpdate);
    }

    @Override
    public void delete(Integer userId) {
        userRepository.deleteById(userId);

    }

    @Override
    public void lockUser(Integer userId) {
        User user = userRepository.getOne(userId);
        if (user.getStatus().equals(Status.ACTIVE)) {
            user.setStatus(Status.INACTIVE);
            userRepository.saveAndFlush(user);
        } else if (user.getStatus().equals(Status.INACTIVE)) {
            user.setStatus(Status.ACTIVE);
            userRepository.saveAndFlush(user);
        }
    }

    @Override
    public List<User> filteredUsers(String username, Role role) {
        List<User> users = userRepository.findAll();
        return users.stream()
                .filter(u -> username == null || u.getUsername().equals(username))
                .filter(u -> role == null || u.getRole().equals(role))
                .collect(Collectors.toList());
    }
}
