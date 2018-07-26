package org.isp.applications.users.api;

import org.isp.applications.users.entity.UserApplication;
import org.isp.applications.users.entity.UserApplicationDto;
import org.isp.applications.users.entity.UserApplicationRepository;
import org.isp.applications.users.entity.UserApplicationStatus;
import org.isp.util.MappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {
    private UserApplicationRepository userApplicationRepository;

    @Autowired
    public UserApplicationServiceImpl(UserApplicationRepository userApplicationRepository) {
        this.userApplicationRepository = userApplicationRepository;
    }

    @Override
    public void approve(Long id) {
        UserApplication userApplication = this.userApplicationRepository.findById(id).get();
        userApplication.setStatus(UserApplicationStatus.APPROVED);
        this.userApplicationRepository.saveAndFlush(userApplication);
    }

    @Override
    public void reject(Long id) {
        UserApplication userApplication = this.userApplicationRepository.findById(id).get();
        userApplication.setStatus(UserApplicationStatus.REJECTED);
        this.userApplicationRepository.saveAndFlush(userApplication);
    }

    @Override
    public void create(UserApplicationDto dto) {
        if (this.userApplicationRepository.findByEmail(dto.getEmail()) != null) {
            throw new IllegalArgumentException("Application with this email already exists!");
        }
        UserApplication userApplication = MappingUtil.convert(dto, UserApplication.class);
        userApplication.setStatus(UserApplicationStatus.WAITING);
        this.userApplicationRepository.saveAndFlush(userApplication);
    }
}
