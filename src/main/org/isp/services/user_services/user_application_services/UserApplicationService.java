package org.isp.services.user_services.user_application_services;

import org.isp.domain.applications.user_applications.UserApplication;
import org.isp.domain.applications.user_applications.UserApplicationDto;
import org.isp.domain.applications.user_applications.UserApplicationStatus;

import java.io.IOException;
import java.util.List;

public interface UserApplicationService {
    void setStatus(String username, UserApplicationStatus status);
    void create(UserApplicationDto userApplicationDto) throws IOException;
    boolean checkIfExists(UserApplicationDto userApplicationDto);
    List<UserApplication> getAllNotApproved();
    UserApplication getByUsername(String username);
}
