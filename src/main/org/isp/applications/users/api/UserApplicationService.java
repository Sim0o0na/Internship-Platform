package org.isp.applications.users.api;

import org.isp.applications.users.entity.UserApplication;
import org.isp.applications.users.entity.UserApplicationDto;
import org.isp.applications.users.entity.UserApplicationStatus;

import java.io.IOException;
import java.util.List;

public interface UserApplicationService {
    void setStatus(String username, UserApplicationStatus status);
    void create(UserApplicationDto userApplicationDto) throws IOException;
    boolean checkIfExists(UserApplicationDto userApplicationDto);
    List<UserApplication> getAllNotApproved();
    UserApplication getByUsername(String username);
}
