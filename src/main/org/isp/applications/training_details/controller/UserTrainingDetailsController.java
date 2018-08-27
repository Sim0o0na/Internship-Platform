package org.isp.applications.training_details.controller;

import org.isp.applications.training_details.entity.TrainingCourse;
import org.isp.applications.training_details.parser.UserInfoParser;
import org.isp.applications.training_details.entity.UserTrainingCourseDetails;
import org.isp.applications.training_details.entity.UserTrainingDetails;
import org.isp.applications.training_details.service.UserTrainingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public UserTrainingDetailsController(@Qualifier(value = "trainingInfoParser") UserInfoParser userInfoParser, UserTrainingDetailsService userTrainingDetailsService) {
        this.userInfoParser = userInfoParser;
        this.userTrainingDetailsService = userTrainingDetailsService;
    }

    public void createUserTrainingDetails(String username) throws IOException {
        UserTrainingDetails userTrainingDetails = new UserTrainingDetails();
        userTrainingDetails.setUsername(username);
        List<UserTrainingCourseDetails> utcdList = this.parseCourseDetailsForUsername(username);
        this.userTrainingDetailsService.createUserTrainingDetails(userTrainingDetails, utcdList,  username);
        this.userTrainingDetailsService.createCourseDetailsForUser(utcdList, username);
    }

    public List<UserTrainingCourseDetails> getCourseDetailsForUsername(String username) {
        return this.userTrainingDetailsService.getCourseDetailsForUsername(username);
    }

    public List<UserTrainingCourseDetails> parseCourseDetailsForUsername(String username) throws IOException {
        List<UserTrainingCourseDetails> coursesDetails = new ArrayList<>();
        HashMap<String, Double> info = this.userInfoParser.getInfo(username);
        for (String course : info.keySet()) {
            UserTrainingCourseDetails utcd = new UserTrainingCourseDetails(new TrainingCourse(course), info.get(course), username);
            coursesDetails.add(utcd);
        }
        return coursesDetails;
    }
}
