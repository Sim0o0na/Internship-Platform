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
        userTrainingDetails.setUsername(username);
        this.trainingDetailsRepository.save(userTrainingDetails);
        this.createCourseDetailsForUser(userTrainingDetails, username);
    }

    public void createCourseDetailsForUser(UserTrainingDetails  userTrainingCourseDetails, String username) {
        // Create course instances in db
        userTrainingCourseDetails.getUserCoursesDetails().forEach(uc -> {
            TrainingCourse currTrainingCourse = uc.getTrainingCourse();
            if (!checkIfCourseExists(currTrainingCourse.getCourseName())) {
                this.trainingCourseRepository.save(currTrainingCourse);
            }
            this.courseDetailsRepository.save(uc);
        });
    }

    private boolean checkIfCourseExists(String courseName) {
        return this.trainingCourseRepository.findByCourseName(courseName) != null;
    }
}
