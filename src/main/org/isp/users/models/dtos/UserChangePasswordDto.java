package org.isp.users.models.dtos;

public class UserChangePasswordDto extends UserDto {
    private String confirmPassword;

    public UserChangePasswordDto() { }

    public UserChangePasswordDto(String password, String confirmPassword) {
        this.setPassword(password);
        this.setConfirmPassword(confirmPassword);
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
