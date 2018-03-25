package org.isp.model.dto;

import org.isp.model.entity.users.Role;
import java.util.Set;

public class UserAdminViewDto extends UserDto {
    public UserAdminViewDto() {
        super();
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }
}
