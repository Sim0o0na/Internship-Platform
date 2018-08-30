package org.isp.domain.users.dtos;

public class UserChangePasswordDto extends UserDto {
    private String newPassword;
    private String confirmPassword;

    public UserChangePasswordDto() { }

    public UserChangePasswordDto(String password, String confirmPassword) {
        this.setPassword(password);
        this.setConfirmPassword(confirmPassword);
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
