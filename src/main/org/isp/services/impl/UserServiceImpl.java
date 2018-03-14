package org.isp.services.impl;

import org.isp.model.dto.UserEditDto;
import org.isp.model.dto.UserRegisterDto;
import org.isp.model.entity.users.Privilege;
import org.isp.model.entity.users.Role;
import org.isp.model.entity.users.User;
import org.isp.repositories.user.RoleRepository;
import org.isp.repositories.user.UserRepository;
import org.isp.services.api.ImageService;
import org.isp.services.api.UserService;
import org.isp.util.PasswordEncoder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private ImageService imageService;

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ImageService imageService,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.modelMapper = new ModelMapper();
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public boolean register(UserRegisterDto userDto) {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            return false;
        }
        User user = this.modelMapper.map(userDto, User.class);
        String encryptedPassword = this.passwordEncoder.encodePassword(userDto.getPassword());
        user.setPassword(encryptedPassword);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);

        Role role = this.roleRepository.findByName("USER");
        user.setRoles(Arrays.asList(role));
        this.userRepository.saveAndFlush(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    @Override
    public UserEditDto findByUsername(String username) throws IOException {
        User user = this.userRepository.findByUsername(username);
        UserEditDto dto = new UserEditDto();
        dto = this.modelMapper.map(user, UserEditDto.class);
        if(user.getProfilePhotoLocation() != null && !user.getProfilePhotoLocation().isEmpty()) {
            dto.setProfilePhoto(this.imageService.getResource(user.getProfilePhotoLocation()));
        } else {
            dto.setProfilePhoto(this.imageService.getDefaultAccountAvatar());
        }
        return dto;
    }

    @Override
    public void edit(String username, UserEditDto userEditDto) {
        User user = this.userRepository.findByUsername(username);
        user.setFacebook(userEditDto.getFacebook());
        user.setInstagram(userEditDto.getInstagram());
        user.setSkype(userEditDto.getSkype());
        user.setProfilePhotoLocation(userEditDto.getProfilePhotoLocation());
        this.userRepository.saveAndFlush(user);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
