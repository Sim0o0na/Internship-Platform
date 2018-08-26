package org.isp.applications.users.api;

import org.isp.applications.users.entity.UserApplication;
import org.isp.applications.users.entity.UserApplicationDto;
import org.isp.applications.users.entity.UserApplicationStatus;

import java.util.List;

public interface UserApplicationService {
    void setStatus(String username, UserApplicationStatus status);
    void create(UserApplicationDto userApplicationDto);
    boolean checkIfExists(UserApplicationDto userApplicationDto);
    List<UserApplication> getAllNotApproved();
    UserApplication getByUsername(String username);
}
