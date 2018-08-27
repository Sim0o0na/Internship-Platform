package org.isp.users.models.dtos;

public class UserChangePasswordDto extends UserDto {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    public UserChangePasswordDto() { }

    public UserChangePasswordDto(String password, String confirmPassword) {
        this.setPassword(password);
        this.setConfirmPassword(confirmPassword);
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
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
