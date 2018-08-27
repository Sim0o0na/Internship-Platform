package org.isp.applications.training_details.parser;

import javafx.util.Pair;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component(value = "trainingInfoParser")
public class UserTrainingInfoParser implements UserInfoParser {
    private static final String USER_ID_PATTERN = "for=\"Id\">Id<\\/label>\\s*([a-zA-Z0-9-]+)\\s*<\\/div>";
    private static final String ENROLLED_MODULES_PATTERN = "aria-controls=\"#module-([1-9]+)\">\\s*([a-zA-Z\\s(]+)";
    private static final String COURSES_IN_ENROLLED_MODULE_PATTERN = "aria-controls=\"#course-([0-9]+)\">\\s*([a-zA-Z\\-0-9\\s+]+)";
    private static final String COURSE_ISTANCES_IN_ENROLLED_COURSE_PATTERN = "aria-controls=\"#course-instance-([0-9]+)\">\\s*([a-zA-Z\\-0-9\\s+]+)";
    private static final String ENROLLED_TRAINING_DETAILS_PATTERN = "ОЦЕНКА:\\s*<strong>(.*)[(]([0-9.]+)[)](.*)<\\/strong>";


    @Override
    public HashMap<String, Double> getInfo(String username, String targetInfo) throws IOException {
        // TODO: Implement method corresponding to targetInfo
        String userId = this.getUserId(username);
        HashMap<String, Integer> enrolledModulesIdsForUser = this.getEnrolledModulesIdsForUser(username);
        HashMap<String, Integer> coursesIdsInEnrolledModules = this.getCoursesIdsInEnrolledModules(userId, enrolledModulesIdsForUser);
        HashMap<String, Integer> allEnrolledCourseInstances = this.getAllEnrolledCourseInstances(userId, coursesIdsInEnrolledModules);
        HashMap<String, Double> enrolledTrainingsDetails = this.getEnrolledTrainingsDetails(userId, allEnrolledCourseInstances);
        return enrolledTrainingsDetails;
    }

    private String getUserId(String username) {
        String userId = "";
        String userDetailsHtml;
        try {
            userDetailsHtml = HTTPRequestSender.sendRequest("https://softuni.bg/users/profile/show/" + username);
        } catch (Exception e) {
            throw new IllegalArgumentException("Username does not exist!");
        }
        Pattern pattern = Pattern.compile(USER_ID_PATTERN);
        Matcher matcher = pattern.matcher(userDetailsHtml);
        if (matcher.find()) {
            userId = matcher.group(1);
        }
        return userId;
    }

    public HashMap<String, Integer> getEnrolledModulesIdsForUser(String username) throws IOException {
        String trainingsPageHtml = HTTPRequestSender.sendRequest("https://softuni.bg/users/profile/trainings/" + username);
        Pattern pattern = Pattern.compile(ENROLLED_MODULES_PATTERN);
        Matcher matcher = pattern.matcher(trainingsPageHtml);
        HashMap<String, Integer> result = new HashMap<>();
        while (matcher.find()){
            result.put(matcher.group(2).trim(), Integer.parseInt(matcher.group(1)));
        }
        return result;
    }

    public HashMap<String, Integer> getCoursesIdsInEnrolledModules(String userId, HashMap<String, Integer> modulesAndModulesIds) throws IOException {
        HashMap<String, Integer> coursesInUserEnrolledLevelIds = new HashMap<>();
        for (String module: modulesAndModulesIds.keySet()) {
            String moduleId = modulesAndModulesIds.get(module).toString();
            List<Pair> coursesAndEnrolledCoursesIdsInModule = this.getCoursesIdsInEnrolledModule(userId, moduleId);
            coursesAndEnrolledCoursesIdsInModule.stream().forEach(pair -> coursesInUserEnrolledLevelIds
                    .put(pair.getKey().toString(),
                            Integer.parseInt(pair.getValue().toString())));
        }
        return coursesInUserEnrolledLevelIds;
    }

    private List<Pair> getCoursesIdsInEnrolledModule(String userId, String moduleId) throws IOException {
        String coursesPartialHtml = HTTPRequestSender.sendRequest("https://softuni.bg/users/trainings/getcoursesinenrolledlevel?userId="
                + userId + "&levelId=" + moduleId);
        Pattern pattern = Pattern.compile(COURSES_IN_ENROLLED_MODULE_PATTERN);
        Matcher matcher = pattern.matcher(coursesPartialHtml);
        Pair pair = null;
        List<Pair> coursesInEnrolledModule = new ArrayList<>();
        while (matcher.find()) {
            pair = new Pair(matcher.group(2).trim(), matcher.group(1));
            coursesInEnrolledModule.add(pair);
        }
        return coursesInEnrolledModule;
    }

    public HashMap<String, Integer> getAllEnrolledCourseInstances(String userId, HashMap<String, Integer> coursesAndCourseIds) throws IOException {
        HashMap<String, Integer> courseInstancesInEnrolledCourse = new HashMap<>();
        for (String course: coursesAndCourseIds.keySet()) {
            List<Pair> courseAndEnrolledCourseInstanceInModule = this.getAllCourseInstancesInCourse(userId, coursesAndCourseIds.get(course).toString());
            courseAndEnrolledCourseInstanceInModule.stream().forEach(pair -> courseInstancesInEnrolledCourse
                    .put(pair.getKey().toString(),
                            Integer.parseInt(pair.getValue().toString())));
        }
        return courseInstancesInEnrolledCourse;
    }

    private List<Pair> getAllCourseInstancesInCourse(String userId, String enrolledCourseId) throws IOException {
        String courseInstancesPartialHtml = HTTPRequestSender.sendRequest(String.format("https://softuni.bg/users/trainings/getcourseinstancesincourse?userId=%s&courseId=%s", userId, enrolledCourseId));
        Pattern pattern = Pattern.compile(COURSE_ISTANCES_IN_ENROLLED_COURSE_PATTERN);
        Matcher matcher = pattern.matcher(courseInstancesPartialHtml);
        Pair pair = null;
        List<Pair> coursesInstancesInEnrolledCourse = new ArrayList<>();
        while (matcher.find()) {
            pair = new Pair(matcher.group(2).trim(), matcher.group(1));
            coursesInstancesInEnrolledCourse.add(pair);
        }
        return coursesInstancesInEnrolledCourse;
    }

    public HashMap<String,Double> getEnrolledTrainingsDetails(String userId, HashMap<String,Integer> coursesInstancesInEnrolledCourses) throws IOException {
        Pattern pattern = Pattern.compile(ENROLLED_TRAINING_DETAILS_PATTERN);
        HashMap<String, Double> trainingResults = new HashMap<>();
        for (String courseInstanceName: coursesInstancesInEnrolledCourses.keySet()) {
            String courseInstancePartialHtml = HTTPRequestSender.sendRequest(String.format("https://softuni.bg/users/trainings/getenrolledtrainingdetails?userId=%s&trainingId=%s",
                    userId,
                    coursesInstancesInEnrolledCourses.get(courseInstanceName)));
            Matcher matcher = pattern.matcher(courseInstancePartialHtml);
            if (matcher.find()) {
                trainingResults.put(courseInstanceName, Double.parseDouble(matcher.group(2)));
            }
        }
        return trainingResults;
    }
}
