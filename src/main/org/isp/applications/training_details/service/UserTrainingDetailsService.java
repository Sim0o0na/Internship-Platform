package org.isp.applications.training_details.service;

import org.isp.applications.training_details.entity.UserTrainingCourseDetails;
import org.isp.applications.training_details.entity.UserTrainingDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserTrainingDetailsService {
    boolean createUserTrainingDetails(UserTrainingDetails userTrainingDetails,
                                      List<UserTrainingCourseDetails> utcdList,
                                      String username);

    @Transactional
    boolean createCourseDetailsForUser(List<UserTrainingCourseDetails> userTrainingCourseDetails, String username);
}
