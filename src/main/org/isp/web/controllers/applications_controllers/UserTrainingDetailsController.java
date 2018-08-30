package org.isp.web.controllers.applications_controllers;

import org.isp.domain.applications.training_details.TrainingCourse;
import org.isp.util.user_info_parser.UserInfoParser;
import org.isp.domain.applications.training_details.UserTrainingCourseDetails;
import org.isp.domain.applications.training_details.UserTrainingDetails;
import org.isp.services.training_details_services.UserTrainingDetailsService;
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
        HashMap<String, Double> info = this.userInfoParser.getInfo(username, "");
        for (String course : info.keySet()) {
            UserTrainingCourseDetails utcd = new UserTrainingCourseDetails(new TrainingCourse(course), info.get(course), username);
            coursesDetails.add(utcd);
        }
        return coursesDetails;
    }
}
