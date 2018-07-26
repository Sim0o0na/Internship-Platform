package org.isp.applications.users.api;

import org.isp.applications.users.entity.UserApplicationDto;

public interface UserApplicationService {
    void approve(Long id);
    void reject(Long id);
    void create(UserApplicationDto dto);
}
