package com.avizhen.validator;

import com.avizhen.dto.UserRegistrationDto;
import com.avizhen.entity.User;
import com.avizhen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Component
public class UserValidator {

    @Autowired
    private UserService userService;

    public void validateOnCreate(UserRegistrationDto userRegistrationDto, Errors errors) {
        commonValidationForUpdateAndCreateUser(userRegistrationDto, errors);
        if (userService.findUserByUsername(userRegistrationDto.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.username");
        }
    }

    private void commonValidationForUpdateAndCreateUser(UserRegistrationDto userRegistrationDto, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "Required");

        if (!userRegistrationDto.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{3,16}$")) {
            errors.rejectValue("password", "Difficult.password");
        }
    }

    public void validateOnUpdate(UserRegistrationDto userRegistrationDto, Errors errors) {
        commonValidationForUpdateAndCreateUser(userRegistrationDto, errors);
        User updatedUser = userService.findUserById(userRegistrationDto.getUserId());
        if (!updatedUser.getUsername().equals(userRegistrationDto.getUsername()) &&
                userService.findUserByUsername(userRegistrationDto.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.username");
        }
    }
}
