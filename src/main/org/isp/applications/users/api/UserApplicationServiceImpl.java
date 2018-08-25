package org.isp.applications.users.api;

import org.isp.applications.training_details.entity.UserTrainingDetails;
import org.isp.applications.users.entity.*;
import org.isp.util.MappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public void create(UserApplicationDto dto, UserTrainingDetails userTrainingDetails) {
        if (this.userApplicationRepository.findByEmail(dto.getEmail()) != null) {
            throw new IllegalArgumentException("Application with this email already exists!");
        } else if (this.userApplicationRepository.findByUsername(dto.getUsername()) != null) {
            throw new IllegalArgumentException("Application with this username already exists!");
        }
        UserApplication userApplication = MappingUtil.convert(dto, UserApplication.class);
        userApplication.setUserTrainingDetails(userTrainingDetails);
        userApplication.setStatus(UserApplicationStatus.WAITING);
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
