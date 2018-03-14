package org.isp.model.dto;

import org.isp.util.validation.IsPasswordsMatching;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Validated(value = IsPasswordsMatching.class)
public class UserRegisterDto {
    @Size(min = 5, max = 15, message = "Username size must be at least 5 characters long!")
    private String username;

    @Size(min = 5, max = 15, message = "Password must be at least 5 characters long!")
    private String password;

    private String firstName;

    private String lastName;

    @Email
    private String email;

    private String confirmPassword;

    public UserRegisterDto() {
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
