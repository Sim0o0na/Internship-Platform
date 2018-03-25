package org.isp.model.dto;

import org.isp.util.validation.IsPasswordsMatching;
import org.springframework.validation.annotation.Validated;

@Validated(value = IsPasswordsMatching.class)
public class UserRegisterDto extends UserDto {
    private String password;

    private String confirmPassword;

    public UserRegisterDto() {
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
}
