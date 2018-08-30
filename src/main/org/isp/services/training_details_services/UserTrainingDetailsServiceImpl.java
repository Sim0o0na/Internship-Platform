package org.isp.services.training_details_services;

import org.isp.domain.applications.training_details.TrainingCourseDto;
import org.isp.domain.applications.user_applications.UserApplication;
import org.isp.repositories.applications_repositories.UserApplicationRepository;
import org.isp.domain.applications.training_details.TrainingCourse;
import org.isp.domain.applications.training_details.UserTrainingCourseDetails;
import org.isp.domain.applications.training_details.UserTrainingDetails;
import org.isp.repositories.applications_repositories.TrainingCourseRepository;
import org.isp.repositories.applications_repositories.UserTrainingCourseDetailsRepository;
import org.isp.repositories.applications_repositories.UserTrainingDetailsRepository;
import org.isp.util.MappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserTrainingDetailsServiceImpl implements UserTrainingDetailsService {
    private UserTrainingCourseDetailsRepository courseDetailsRepository;
    private UserTrainingDetailsRepository trainingDetailsRepository;
    private TrainingCourseRepository trainingCourseRepository;
    private UserApplicationRepository userApplicationRepository;

    @Autowired
    public UserTrainingDetailsServiceImpl(UserTrainingCourseDetailsRepository courseDetailsRepository,
                                          UserTrainingDetailsRepository trainingDetailsRepository,
                                          TrainingCourseRepository trainingCourseRepository,
                                          UserApplicationRepository userApplicationRepository) {
        this.courseDetailsRepository = courseDetailsRepository;
        this.trainingDetailsRepository = trainingDetailsRepository;
        this.trainingCourseRepository = trainingCourseRepository;
        this.userApplicationRepository = userApplicationRepository;
    }

    @Override
    @Transactional
    public boolean createUserTrainingDetails(UserTrainingDetails userTrainingDetails,
                                             List<UserTrainingCourseDetails> utcdList,
                                             String username) {
        // Create course instances with grades for user
        userTrainingDetails.setUsername(username);
        userTrainingDetails.setAverageGrade(this.calculateAverageTrainingResult(utcdList));
        UserApplication userApplication = this.userApplicationRepository.findByUsername(username);
        userTrainingDetails.setUserApplication(userApplication);
        this.trainingDetailsRepository.saveAndFlush(userTrainingDetails);
        userApplication.setUserTrainingDetails(userTrainingDetails);
        this.userApplicationRepository.saveAndFlush(userApplication);
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

    @Override
    public List<TrainingCourseDto> getCourseDetailsForUsername(String username) {
        List<UserTrainingCourseDetails> allCoursesForUser = new ArrayList<>();
        allCoursesForUser = this.courseDetailsRepository.findAllByUsername(username);
        List<TrainingCourseDto> result = MappingUtil.convert(allCoursesForUser, TrainingCourseDto.class);
        if (allCoursesForUser.size() == 0) {
            throw new IllegalArgumentException("No course details for this username!");
        }
        return result;
    }

    @Override
    public double getAverageGradeForUsername(String usenname) {
        return this.trainingDetailsRepository.findByUsername(usenname).getAverageGrade();
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
