package org.isp.applications.training_details.controller;

import org.isp.applications.training_details.entity.TrainingCourse;
import org.isp.applications.training_details.parser.UserInfoParser;
import org.isp.applications.training_details.entity.UserTrainingCourseDetails;
import org.isp.applications.training_details.entity.UserTrainingDetails;
import org.isp.applications.training_details.service.UserTrainingDetailsService;
import org.isp.applications.training_details.service.UserTrainingDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class UserTrainingDetailsController {
    private UserInfoParser userInfoParser;
    private UserTrainingDetailsService userTrainingDetailsService;

    @Autowired
    public UserTrainingDetailsController(UserInfoParser userInfoParser, UserTrainingDetailsService userTrainingDetailsService) {
        this.userInfoParser = userInfoParser;
        this.userTrainingDetailsService = userTrainingDetailsService;
    }

    public UserTrainingDetails getUserTrainingDetails(String username) throws IOException {
        UserTrainingDetails userTrainingDetails = new UserTrainingDetails();
        userTrainingDetails.setUserCoursesDetails(this.getCoursesDetailsForUser(username, userTrainingDetails));
        return userTrainingDetails;
    }

    public void createUserTrainingDetails(UserTrainingDetails userTrainingDetails, String username) {
        this.userTrainingDetailsService.create(userTrainingDetails, username);
    }

    private List<UserTrainingCourseDetails> getCoursesDetailsForUser(String username, UserTrainingDetails userTrainingDetails) throws IOException {
        List<UserTrainingCourseDetails> coursesDetails = new ArrayList<>();
        HashMap<String, Double> info = this.userInfoParser.getInfo(username);
        for (String course : info.keySet()) {
            coursesDetails.add(new UserTrainingCourseDetails(new TrainingCourse(course), info.get(course), userTrainingDetails));
        }
        return coursesDetails;
    }
}
