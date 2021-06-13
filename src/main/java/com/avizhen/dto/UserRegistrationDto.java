package com.avizhen.dto;

import com.avizhen.enums.Role;
import com.avizhen.enums.Status;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegistrationDto {

    @Size(min = 3, max = 16, message
            = "Username must be between 3 and 16 characters")
    @Pattern(regexp="[a-z]*", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Only latin  letters")
    private String username;
    private String password;
    @Size(min = 1, max = 16, message
            = "First name must be between 3 and 16 characters")
    @Pattern(regexp="[a-z]*", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Only latin  letters")
    private String firstName;
    @Size(min = 1, max = 16, message
            = "Last name must be between 3 and 16 characters")
    @Pattern(regexp="[a-z]*", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Only latin  letters")
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
