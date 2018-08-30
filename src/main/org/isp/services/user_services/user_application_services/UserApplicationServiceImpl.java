package org.isp.services.user_services.user_application_services;

import org.isp.domain.applications.user_applications.UserApplication;
import org.isp.domain.applications.user_applications.UserApplicationDto;
import org.isp.domain.applications.user_applications.UserApplicationStatus;
import org.isp.repositories.applications_repositories.UserApplicationRepository;
import org.isp.util.user_info_parser.UserPersonalInfoParser;
import org.isp.util.MappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {
    private UserApplicationRepository userApplicationRepository;
    private UserPersonalInfoParser userPersonalInfoParser;

    @Autowired
    public UserApplicationServiceImpl(UserApplicationRepository userApplicationRepository,
                                      @Qualifier(value = "personalInfoParser") UserPersonalInfoParser userPersonalInfoParser) {
        this.userApplicationRepository = userApplicationRepository;
        this.userPersonalInfoParser = userPersonalInfoParser;
    }

    @Override
    public void setStatus(String username, UserApplicationStatus status) {
        UserApplication userApplication = this.userApplicationRepository.findByUsername(username);
        if (userApplication == null) {
            throw new IllegalArgumentException("User application does not exist for this username!");
        }
        userApplication.setStatus(status);
        this.userApplicationRepository.saveAndFlush(userApplication);
    }

    @Override
    public void create(UserApplicationDto dto) throws IOException {
        if (this.userApplicationRepository.findByEmail(dto.getEmail()) != null) {
            throw new IllegalArgumentException("Application with this email already exists!");
        } else if (this.userApplicationRepository.findByUsername(dto.getUsername()) != null) {
            throw new IllegalArgumentException("Application with this username already exists!");
        }
        UserApplication userApplication = MappingUtil.convert(dto, UserApplication.class);
        userApplication.setEmail(this.userPersonalInfoParser.getInfo(dto.getUsername(), "email").get("email"));
        userApplication.setStatus(UserApplicationStatus.WAITING);
        userApplication.setAppliedOn(LocalDateTime.now());
        this.userApplicationRepository.saveAndFlush(userApplication);
    }

    @Override
    public boolean checkIfExists(UserApplicationDto dto) {
        return this.userApplicationRepository.findByUsername(dto.getUsername()) != null &&
                this.userApplicationRepository.findByEmail(dto.getEmail()) != null;
    }

    @Override
    public List<UserApplication> getAllNotApproved() {
        return this.userApplicationRepository.findAll()
                .stream()
                .filter(ua -> ua.getStatus().toString().equals("WAITING"))
                .collect(Collectors.toList());
    }

    @Override
    public UserApplication getByUsername(String username) {
        UserApplication userApplication = this.userApplicationRepository.findByUsername(username);
        if (userApplication == null) {
            throw new IllegalArgumentException("Application with this username does not exist!");
        }
        return userApplication;
    }
}
