package com.avizhen.converter.impl;

import com.avizhen.converter.Converter;
import com.avizhen.dto.UserRegistrationDto;
import com.avizhen.entity.User;
import com.avizhen.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<User, UserRegistrationDto> {
    @Override
    public User convertToEntity(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setPassword(userRegistrationDto.getPassword());
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());
        user.setRole(userRegistrationDto.getRole());
        user.setStatus(userRegistrationDto.getStatus());

        return user;
    }

    @Override
    public User convertEntityToUpdate(UserRegistrationDto dto, User entity) {
        return null;
    }
}