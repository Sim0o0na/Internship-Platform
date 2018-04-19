package org.isp.users.services;

import org.isp.users.models.dtos.UserAdminViewDto;
import org.isp.users.models.dtos.UserDto;
import org.isp.users.models.dtos.UserEditDto;
import org.isp.users.models.dtos.UserRegisterDto;
import org.isp.users.models.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService<T extends UserDto> extends UserDetailsService {
    void register(UserRegisterDto userDto);

    T findByUsername(String username, Class<T> dtoClass) throws Exception;

    User findByUsername(String username);

    void edit(String username, UserEditDto userEditDto);

    List<UserAdminViewDto> fetchAllUsers();
}
