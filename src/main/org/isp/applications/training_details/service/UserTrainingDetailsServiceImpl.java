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
    @Transactional
    public boolean createUserTrainingDetails(UserTrainingDetails userTrainingDetails,
                                             List<UserTrainingCourseDetails> utcdList,
                                             String username) {
        // Create course instances with grades for user
        userTrainingDetails.setUsername(username);
        userTrainingDetails.setAverageGrade(this.calculateAverageTrainingResult(utcdList));
        this.trainingDetailsRepository.saveAndFlush(userTrainingDetails);
        return true;
    }

    @Override
    @Transactional
    public boolean createCourseDetailsForUser(List<UserTrainingCourseDetails> userTrainingCourseDetails, String username) {
        // Create course instances in db
        userTrainingCourseDetails.forEach(uc -> {
            TrainingCourse currTrainingCourse = uc.getTrainingCourse();
            if (!checkIfCourseExists(currTrainingCourse.getCourseName())) {
                this.trainingCourseRepository.saveAndFlush(currTrainingCourse);
            } else {
                currTrainingCourse = this.trainingCourseRepository.findByCourseName(currTrainingCourse.getCourseName());
                uc.setTrainingCourse(currTrainingCourse);
            }
            this.courseDetailsRepository.saveAndFlush(uc);
        });
        return true;
    }

    private boolean checkIfCourseExists(String courseName) {
        return this.trainingCourseRepository.findByCourseName(courseName) != null;
    }

    private double calculateAverageTrainingResult(List<UserTrainingCourseDetails> userTrainingCourseDetails) {
        double sum = userTrainingCourseDetails.stream().mapToDouble(UserTrainingCourseDetails::getGrade).sum();
        sum /= userTrainingCourseDetails.size();
        return sum;
    }
}
