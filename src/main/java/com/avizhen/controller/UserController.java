package com.avizhen.controller;

import com.avizhen.entity.User;
import com.avizhen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
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
}
