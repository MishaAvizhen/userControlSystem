package com.avizhen.controller;

import com.avizhen.dto.UserRegistrationDto;
import com.avizhen.entity.User;
import com.avizhen.enums.Role;
import com.avizhen.service.UserService;
import com.avizhen.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    private UserService userService;
    private UserValidator userValidator;


    @Autowired
    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/user")
    public String showUsersList(Model model, String username, Role role) {
        List<User> allUsers = userService.filteredUsers(username, role);
        model.addAttribute("users", allUsers);
        return "list";
    }

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable int id, Model model) {
        User userById = userService.findUserById(id);
        model.addAttribute("user", userById);
        return "view";
    }

    @GetMapping("user/new")
    public String newUser(Model model) {
        model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        return "new";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable int id) {
        User userById = userService.findUserById(id);
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", userById);
        }
        return "edit";
    }

    @PostMapping("/user")
    public String createUser(@Valid UserRegistrationDto userRegistrationDto,
                             BindingResult bindingResult, Model model) {
        userValidator.validate(userRegistrationDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/new";
        }
        User user = userService.registerUser(userRegistrationDto);
        model.addAttribute("userRegistrationDto", user);

        return "redirect:/user";
    }



    @PatchMapping("/user/{id}")
    public String updateUser(@Valid @ModelAttribute("user") UserRegistrationDto userRegistrationDto,
                             BindingResult bindingResult, @PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        userValidator.validate(userRegistrationDto, bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", userService.findUserById(id));
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            return "redirect:/" + id + "/edit";
        }
        userService.updateUser(id, userRegistrationDto);
        return "redirect:/user";
    }

    @DeleteMapping("/user/{id}")
    public String delete(@PathVariable int id) {
        userService.delete(id);
        return "redirect:/user";
    }

    @PatchMapping("/user/{id}/lock")
    public String lockUser(@PathVariable int id) {
        userService.lockUser(id);
        return "redirect:/user/{id}";
    }
}
