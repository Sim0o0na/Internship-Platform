package org.isp.util.user_info_parser;

import javafx.util.Pair;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component(value = "trainingInfoParser")
public class UserTrainingInfoParser implements UserInfoParser {
    private static final String USER_ID_PATTERN = "for=\"Id\">Id<\\/label>\\s*([a-zA-Z0-9-]+)\\s*<\\/div>";
    private static final String ENROLLED_MODULES_PATTERN = "aria-controls=\"#module-([0-9]+)\">\\s*([a-zA-Z\\#\\s(]+)";
    private static final String COURSES_IN_ENROLLED_MODULE_PATTERN = "aria-controls=\"#course-([0-9]+)\">\\s*([a-zA-Z\\-0-9\\s\\#+]+)";
    private static final String COURSE_ISTANCES_IN_ENROLLED_COURSE_PATTERN = "aria-controls=\"#course-instance-([0-9]+)\">\\s*([a-zA-Z\\#\\-0-9\\s+]+)";
    private static final String ENROLLED_TRAINING_DETAILS_PATTERN = "ОЦЕНКА:\\s*<strong>(.*)[(]([0-9.]+)[)](.*)<\\/strong>";
    private static final String NO_ENROLLED_TRAININGS_BG_STRING = "НЯМА ЗАПИСАНИ МОДУЛИ.";

    private static final String TRAININGS_BASE_URL = "https://softuni.bg/users/profile/trainings/%s";
    private static final String USER_PROFILE_PAGE_URL = "https://softuni.bg/users/profile/show/%s";


    @Override
    public HashMap<String, String> getInfo(String username, String targetInfo) throws IOException {
        // TODO: Implement method corresponding to targetInfo
        String userId = this.getUserId(username);
        HashMap<String, String> enrolledModulesIdsForUser = this.getEnrolledModulesIdsForUser(username);
        HashMap<String, Integer> coursesIdsInEnrolledModules = this.getCoursesIdsInEnrolledModules(userId, enrolledModulesIdsForUser);
        HashMap<String, Integer> allEnrolledCourseInstances = this.getAllEnrolledCourseInstances(userId, coursesIdsInEnrolledModules);
        HashMap<String, String> enrolledTrainingsDetails = this.getEnrolledTrainingsDetails(userId, allEnrolledCourseInstances);
        return enrolledTrainingsDetails;
    }

    private List<Pair> parseTrainingWebPage(String matchPattern, String pageUrl) throws IOException {
        String trainingsPageHtml = HTTPRequestSender.sendRequest(pageUrl);
        Pattern pattern = Pattern.compile(matchPattern);
        Matcher matcher = pattern.matcher(trainingsPageHtml);
        List<Pair> result = new ArrayList<>();
        while (matcher.find()){
            result.add(new Pair(matcher.group(2).trim(), Integer.parseInt(matcher.group(1))));
        }
        return result;
    }

    @Override
    public boolean isInfoAvailable(String username) throws IOException {
        String userId = this.getUserId(username);
        String trainingsPageHtml = HTTPRequestSender.sendRequest(String.format(TRAININGS_BASE_URL, username));
        return !trainingsPageHtml.contains(NO_ENROLLED_TRAININGS_BG_STRING);
    }

    private Pair parseSingleDataInTrainingWebPage(String matchPattern, String pageUrl) throws IOException {
        String trainingsPageHtml = HTTPRequestSender.sendRequest(pageUrl);
        Pattern pattern = Pattern.compile(matchPattern);
        Matcher matcher = pattern.matcher(trainingsPageHtml);
        Pair result = null;
        if (matcher.find()){
            result = new Pair(matcher.group(2).trim(), matcher.group(1));
        }
        return result;
    }

    private String getUserId(String username) {
        String userId = "";
        String userDetailsHtml;
        try {
            userDetailsHtml = HTTPRequestSender.sendRequest(String.format(USER_PROFILE_PAGE_URL, username));
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

    public HashMap<String, String> getEnrolledModulesIdsForUser(String username) throws IOException {
        HashMap<String, String> result = new HashMap<>();
        parseTrainingWebPage(ENROLLED_MODULES_PATTERN, String.format(TRAININGS_BASE_URL, username))
                .stream().forEach(pair -> result.put(pair.getKey().toString(), pair.getValue().toString()));
        return result;
    }

    public HashMap<String, Integer> getCoursesIdsInEnrolledModules(String userId, HashMap<String, String> modulesAndModulesIds) throws IOException {
        HashMap<String, Integer> coursesInUserEnrolledLevelIds = new HashMap<>();
        for (String module: modulesAndModulesIds.keySet()) {
            String moduleId = modulesAndModulesIds.get(module);
            List<Pair> coursesAndEnrolledCoursesIdsInModule = this.getCoursesIdsInEnrolledModule(userId, moduleId);
            coursesAndEnrolledCoursesIdsInModule.stream().forEach(pair -> coursesInUserEnrolledLevelIds
                    .put(pair.getKey().toString(),
                            Integer.parseInt(pair.getValue().toString())));
        }
        return coursesInUserEnrolledLevelIds;
    }

    private List<Pair> getCoursesIdsInEnrolledModule(String userId, String moduleId) throws IOException {
        List<Pair> coursesInEnrolledModule = this.parseTrainingWebPage(COURSES_IN_ENROLLED_MODULE_PATTERN,
               "https://softuni.bg/users/trainings/getcoursesinenrolledlevel?userId="
                        + userId + "&levelId=" + moduleId);
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
        List<Pair> coursesInstancesInEnrolledCourse = this.parseTrainingWebPage(COURSE_ISTANCES_IN_ENROLLED_COURSE_PATTERN,
                String.format("https://softuni.bg/users/trainings/getcourseinstancesincourse?userId=%s&courseId=%s",
                        userId, enrolledCourseId));
        return coursesInstancesInEnrolledCourse;
    }

    public HashMap<String, String> getEnrolledTrainingsDetails(String userId, HashMap<String,Integer> coursesInstancesInEnrolledCourses) throws IOException {
        Pattern pattern = Pattern.compile(ENROLLED_TRAINING_DETAILS_PATTERN);
        HashMap<String, String> trainingResults = new HashMap<>();
        for (String courseInstanceName: coursesInstancesInEnrolledCourses.keySet()) {
            Pair result = parseSingleDataInTrainingWebPage(ENROLLED_TRAINING_DETAILS_PATTERN,
                    String.format("https://softuni.bg/users/trainings/getenrolledtrainingdetails?userId=%s&trainingId=%s",
                            userId,
                            coursesInstancesInEnrolledCourses.get(courseInstanceName)));
            if (result == null) {
                continue;
            }
            trainingResults.put(courseInstanceName, result.getKey().toString());
        }
        return trainingResults;
    }
}
