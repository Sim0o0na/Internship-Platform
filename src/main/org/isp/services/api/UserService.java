package org.isp.services.api;

import org.isp.model.dto.UserEditDto;
import org.isp.model.dto.UserRegisterDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

public interface UserService extends UserDetailsService {
    boolean register(UserRegisterDto userDto);
    UserEditDto findByUsername(String username) throws IOException;
    void edit(String username, UserEditDto userEditDto);
}
