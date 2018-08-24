package org.isp.applications.training_details.service;

import org.isp.applications.training_details.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserTrainingDetailsServiceImpl implements UserTrainingDetailsService {
    private UserTrainingCourseDetailsRepository courseDetailsRepository;
    private UserTrainingDetailsRepository trainingDetailsRepository;
    private TrainingCourseRepository trainingCourseRepository;

    @Autowired
    public UserTrainingDetailsServiceImpl(UserTrainingCourseDetailsRepository courseDetailsRepository,
                                          UserTrainingDetailsRepository trainingDetailsRepository,
                                          TrainingCourseRepository trainingCourseRepository) {
        this.courseDetailsRepository = courseDetailsRepository;
        this.trainingDetailsRepository = trainingDetailsRepository;
        this.trainingCourseRepository = trainingCourseRepository;
    }

    @Override
    public void create(UserTrainingDetails userTrainingDetails, String username) {
        // Create course instances with grades for user
        this.createCourseDetailsForUser(userTrainingDetails.getUserCoursesDetails(), username);
        this.trainingDetailsRepository.save(userTrainingDetails);
    }

    public void createCourseDetailsForUser(List<UserTrainingCourseDetails> userTrainingCourseDetailsList, String username) {
        // Create course instances in db
        userTrainingCourseDetailsList.forEach(uc -> {
            this.trainingCourseRepository.save(uc.getTrainingCourse());
            this.courseDetailsRepository.save(uc);
        });
    }


}
