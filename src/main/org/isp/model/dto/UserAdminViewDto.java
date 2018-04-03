package org.isp.model.dto;

public class UserAdminViewDto extends UserDto {
    public UserAdminViewDto() {
        super();
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }
}
