package org.isp.users.models.dtos;

import org.isp.util.validation.IsPasswordsMatching;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated(value = IsPasswordsMatching.class)
public class UserRegisterDto extends UserDto {

    @NotNull(message = "Passwords don't match!")
    private String confirmPassword;

    public UserRegisterDto() {
        super();
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
