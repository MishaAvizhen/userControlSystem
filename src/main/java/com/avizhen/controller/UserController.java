package com.avizhen.controller;

import com.avizhen.dto.UserRegistrationDto;
import com.avizhen.entity.User;
import com.avizhen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String showUsersList(Model model) {
        List<User> allUsers = userService.findAllUsers();
        model.addAttribute("users", allUsers);
        return "index";
    }
    @GetMapping("/user/{id}")
    public String getUser(@PathVariable int id, Model model) {
        User userById = userService.findUserById(id);
        model.addAttribute("user", userById);
        return "user";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("userRegistrationDto") UserRegistrationDto userRegistrationDto) {
        return "new";
    }

    @PostMapping("/user")
    public String createUser( @ModelAttribute("userRegistrationDto") UserRegistrationDto userRegistrationDto) {
         userService.registerUser(userRegistrationDto);
        return "redirect:/users";
    }
}
