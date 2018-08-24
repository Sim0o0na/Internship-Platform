package org.isp.applications.users.api;

import org.isp.applications.users.entity.UserApplicationDto;
import org.isp.applications.training_details.entity.UserTrainingDetails;

public interface UserApplicationService {
    void approve(Long id);
    void reject(Long id);
    void create(UserApplicationDto userApplicationDto, UserTrainingDetails dto);
    boolean checkIfExists(UserApplicationDto userApplicationDto);
}
