package org.isp.users.services;

import org.isp.applications.users.entity.UserApplication;
import org.isp.emails.EmailService;
import org.isp.users.models.dtos.*;
import org.isp.users.models.entities.Privilege;
import org.isp.users.models.entities.Role;
import org.isp.users.models.entities.User;
import org.isp.users.repositories.RoleRepository;
import org.isp.users.repositories.UserRepository;
import org.isp.util.MappingUtil;
import org.isp.util.PasswordEncoder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl<T extends UserDto> implements UserService<T> {
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private RoleRepository roleRepository;
    private EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           EmailService emailService) {
        this.modelMapper = new ModelMapper();
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public void register(UserRegisterDto userDto) {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords don't match!");
        }
        User user = this.modelMapper.map(userDto, User.class);
        String encryptedPassword = PasswordEncoder.encodePassword(userDto.getPassword());
        user.setPassword(encryptedPassword);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);

        Role role = this.roleRepository.findByName("ROLE_USER");
        user.getRoles().add(role);
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public T findByUsername(String username, Class<T> dtoClass) throws Exception {
        User user = this.userRepository.findByUsername(username);
        Constructor<?> constructor = Arrays.stream(dtoClass.getConstructors()).filter(c -> c.getParameterCount() == 0).findFirst().get();
        T dto = (T) constructor.newInstance();
        dto = MappingUtil.convert(user, dtoClass);
        return dto;
    }

    @Override
    public User findByUsername(String username) {
        User user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
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

    @Override
    public List<UserAdminViewDto> fetchAllUsers() {
        List<User> allUsers = this.userRepository.findAll();
        List<UserAdminViewDto> dtos = MappingUtil.convert(allUsers, UserAdminViewDto.class);
        return dtos;
    }

    @Override
    public void createUser(UserApplication userApplication) {
        User user = MappingUtil.convert(userApplication, User.class);
        String encryptedPassword = PasswordEncoder.encodePassword(userApplication.getUsername());
        user.setPassword(encryptedPassword);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);

        Role role = this.roleRepository.findByName("ROLE_USER");
        user.getRoles().add(role);
        this.userRepository.saveAndFlush(user);
        this.emailService.sendMessage(user.getEmail(), "SoftUni internship approvement",
                "You have been approved for the SoftUni internship program! " +
                        "Your login password is currently your username. " +
                        "Please, change the password immediately after first login!");
    }

    @Override
    public void changeUserPassword(String username, UserChangePasswordDto dto) {
        User user = this.userRepository.findByUsername(username);
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("New password does not match confirm password!");
        }
        user.setPassword(PasswordEncoder.encodePassword(dto.getConfirmPassword()));
        user.setIsPasswordChanged(true);
        this.userRepository.saveAndFlush(user);
    }
}
