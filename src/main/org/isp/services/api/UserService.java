package org.isp.services.api;

import org.isp.model.dto.UserAdminViewDto;
import org.isp.model.dto.UserDto;
import org.isp.model.dto.UserEditDto;
import org.isp.model.dto.UserRegisterDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService<T extends UserDto> extends UserDetailsService {
    void register(UserRegisterDto userDto);

    T findByUsername(String username, Class<T> dtoClass) throws Exception;

    void edit(String username, UserEditDto userEditDto);

    List<UserAdminViewDto> fetchAllUsers();
}
