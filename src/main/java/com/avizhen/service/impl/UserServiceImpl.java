package com.avizhen.service.impl;

import com.avizhen.converter.impl.UserConverter;
import com.avizhen.dto.UserRegistrationDto;
import com.avizhen.entity.User;
import com.avizhen.enums.Role;
import com.avizhen.enums.Status;
import com.avizhen.repository.UserRepository;
import com.avizhen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public User findUserById(Integer id) {
        return userRepository.findById(id).get();
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
    public void updateUser(Integer userId, UserRegistrationDto userRegistrationDto) {
        User userToUpdate = userRepository.findById(userId).get();
        userToUpdate.setUsername(userRegistrationDto.getUsername());
        userToUpdate.setFirstName(userRegistrationDto.getFirstName());
        userToUpdate.setLastName(userRegistrationDto.getLastName());
        userToUpdate.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        userToUpdate.setRole(userRegistrationDto.getRole());
        userToUpdate.setStatus(userRegistrationDto.getStatus());
        userRepository.save(userToUpdate);
    }

    @Override
    public void delete(Integer userId) {
        userRepository.deleteById(userId);

    }

    @Override
    public void lockUser(Integer userId) {
        User user = userRepository.findById(userId).get();
        if (user.getStatus().equals(Status.ACTIVE)) {
            user.setStatus(Status.INACTIVE);
            userRepository.save(user);
        } else if (user.getStatus().equals(Status.INACTIVE)) {
            user.setStatus(Status.ACTIVE);
            userRepository.save(user);
        }
    }

    @Override
    public Page<User> findAllUsers(int pageNumber, String username, String firstName) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        if (StringUtils.isEmpty(username) && StringUtils.isEmpty(firstName)) {
            return userRepository.findAll(pageable);
        } else if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(firstName)) {
            return userRepository.findByFirstNameAndUsername(firstName, username, pageable);
        } else if (!StringUtils.isEmpty(username)) {
            return userRepository.findByUsername(username, pageable);
        } else {
            return userRepository.findByFirstName(firstName, pageable);
        }
    }

    @Override
    public Page<User> findAllUsers(int pageNumber) {
        return findAllUsers(pageNumber, null, null);
    }
}
