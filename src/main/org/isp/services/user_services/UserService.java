package org.isp.services.user_services;

import org.isp.domain.applications.user_applications.UserApplication;
import org.isp.domain.users.dtos.*;
import org.isp.domain.users.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService<T extends UserDto> extends UserDetailsService {
    void register(UserRegisterDto userDto);

    T findByUsername(String username, Class<T> dtoClass) throws Exception;

    User findByUsername(String username);

    void edit(String username, UserEditDto userEditDto);

    List<UserAdminViewDto> fetchAllUsers();

    void createUser(UserApplication userApplication);

    void changeUserPassword(String username, UserChangePasswordDto dto);
}
