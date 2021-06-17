package com.avizhen.controller;

import com.avizhen.dto.UserRegistrationDto;
import com.avizhen.entity.User;
import com.avizhen.enums.Role;
import com.avizhen.service.UserService;
import com.avizhen.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public String showUsersList(Model model) {
        model.asMap().remove("username");
        model.asMap().remove("firstName");
        return showUsersListByPage(model, 1);
    }

    @GetMapping("/searchUser")
    public String showUsersListFiltered(Model model, @RequestParam(value = "username", required = false) String username,
                                        @RequestParam(value = "firstName", required = false) String firstName) {
        model.addAttribute("username", username);
        model.addAttribute("firstName", firstName);
        return showUsersListByPage(model, 1);
    }

    @GetMapping("/page/{pageNumber}")
    public String showUsersListByPage(Model model, @PathVariable("pageNumber") int currentPage) {

        String username = (String) model.asMap().get("username");
        String firstName = (String) model.asMap().get("firstName");
        Page<User> userPage = userService.findAllUsers(currentPage, username, firstName);
        long totalElements = userPage.getTotalElements();
        int totalPages = userPage.getTotalPages();
        List<User> allUsers = userPage.getContent();
        model.addAttribute("totalElements", totalElements);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
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
        userValidator.validateOnCreate(userRegistrationDto, bindingResult);
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
        userRegistrationDto.setUserId(id);
        userValidator.validateOnUpdate(userRegistrationDto, bindingResult);
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
