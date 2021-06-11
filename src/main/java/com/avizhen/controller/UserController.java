package com.avizhen.controller;

import com.avizhen.dto.UserRegistrationDto;
import com.avizhen.entity.User;
import com.avizhen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
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
    public String newUser(Model model) {
        model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        return "new";
    }

    @PostMapping("/user")
    public String createUser(@Valid UserRegistrationDto userRegistrationDto,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "/new";
        User user = userService.registerUser(userRegistrationDto);
        model.addAttribute("userRegistrationDto", user);

        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable int id) {
        User userById = userService.findUserById(id);
        model.addAttribute("user", userById);
        return "edit";
    }

    @PatchMapping("/user/{id}")
    public String updateUser(@ModelAttribute("user") UserRegistrationDto userRegistrationDto,
                         @PathVariable int id) {
        userService.updateUser(id, userRegistrationDto);
        return "redirect:/users";
    }

    @DeleteMapping("/user/{id}")
    public String delete(@PathVariable int id) {
        userService.delete(id);
        return "redirect:/users";
    }
}
