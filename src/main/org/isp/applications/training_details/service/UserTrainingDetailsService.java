package org.isp.applications.training_details.service;

import org.isp.applications.training_details.entity.UserTrainingDetails;

public interface UserTrainingDetailsService {

    void create(UserTrainingDetails userTrainingDetails, String username);
}
