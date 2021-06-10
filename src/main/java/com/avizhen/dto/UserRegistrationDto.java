package com.avizhen.dto;

import com.avizhen.enums.Role;
import com.avizhen.enums.Status;

public class UserRegistrationDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Status status;
    private Role role;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String username, String password, String firstName, String lastName, Status status, Role role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
