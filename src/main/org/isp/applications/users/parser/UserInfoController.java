package org.isp.applications.users.parser;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserInfoController {
    private UserInfoParser userInfoParser;
    @Value("${softuni.auth.cookie}")
    private String cookie;

    @Autowired
    public UserInfoController(UserInfoParser userInfoParser) {
        this.userInfoParser = userInfoParser;
    }

    public void getUserInfo(String username) throws Exception {
        String userId = this.getUserId(username);
        HashMap<String, Integer> modulesAndModulesIds = this.getEnrolledModulesForUser(username);
        HashMap<String, Integer> coursesInUserEnrolledLevelIds = this.getCoursesIdsInEnrolledCourses(userId, modulesAndModulesIds);
        HashMap<String, Integer> coursesInstancesInEnrolledCourses = this.getCoursesInstancesIdsInEnrolledCourses(userId, coursesInUserEnrolledLevelIds);
        HashMap<String, Double> enrolledTrainingsDetails = this.getEnrolledTrainingsDetails(userId, coursesInstancesInEnrolledCourses);
    }

    private HashMap<String,Double> getEnrolledTrainingsDetails(String userId, HashMap<String,Integer> coursesInstancesInEnrolledCourses) throws IOException {
        Pattern pattern = Pattern.compile("ОЦЕНКА:\\s*<strong>(.*)[(]([0-9.]+)[)](.*)<\\/strong>");
        HashMap<String, Double> trainingResults = new HashMap<>();
        for (String courseInstanceName: coursesInstancesInEnrolledCourses.keySet()) {
            String courseInstancePartialHtml = this.sendRequest(String.format("https://softuni.bg/users/trainings/getenrolledtrainingdetails?userId=%s&trainingId=%s",
                    userId,
                    coursesInstancesInEnrolledCourses.get(courseInstanceName)));
            Matcher matcher = pattern.matcher(courseInstancePartialHtml);
            if (matcher.find()) {
                trainingResults.put(courseInstanceName, Double.parseDouble(matcher.group(2)));
            }
        }
        return trainingResults;
    }


    private String sendRequest(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Cookie", cookie);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuffer response = new StringBuffer();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            response.append(line);
        }
        bufferedReader.close();
        return response.toString();
    }

    private String getUserId(String username) throws IOException {
        String userId = "";
        String userDetailsHtml = this.sendRequest("https://softuni.bg/users/profile/show/" + username);
        Pattern pattern = Pattern.compile("showavatar/([a-zA-Z0-9\\-]+)");
        Matcher matcher = pattern.matcher(userDetailsHtml);
        if (matcher.find()) {
            userId = matcher.group(1);
        }
        return userId;
    }

    private HashMap<String, Integer> getEnrolledModulesForUser(String username) throws IOException {
        String userTrainingsPageHtml = this.sendRequest("https://softuni.bg/users/profile/trainings/" + username);
        Pattern pattern = Pattern.compile("aria-controls=\"#module-([1-9]+)\">\\s*([a-zA-Z\\s(]+)");
        Matcher matcher = pattern.matcher(userTrainingsPageHtml);
        HashMap<String, Integer> result = new HashMap<>();
        while (matcher.find()){
            result.put(matcher.group(2).trim(), Integer.parseInt(matcher.group(1)));
        }
        return result;
    }

    private HashMap<String, Integer> getCoursesIdsInEnrolledCourses(String userId, HashMap<String, Integer> modulesAndModulesIds) throws IOException {
        HashMap<String, Integer> coursesInUserEnrolledLevelIds = new HashMap<>();
        for (String module: modulesAndModulesIds.keySet()) {
            String moduleId = modulesAndModulesIds.get(module).toString();
            List<Pair> coursesAndEnrolledCoursesIdsInModule = this.getCoursesIdsInEnrolledLevelByUser(userId, moduleId);
            coursesAndEnrolledCoursesIdsInModule.stream().forEach(pair -> coursesInUserEnrolledLevelIds
                    .put(pair.getKey().toString(),
                            Integer.parseInt(pair.getValue().toString())));
        }
        return coursesInUserEnrolledLevelIds;
    }

    private List<Pair> getCoursesIdsInEnrolledLevelByUser(String userId, String moduleId) throws IOException {
        String coursesPartialHtml = this.sendRequest("https://softuni.bg/users/trainings/getcoursesinenrolledlevel?userId="
                + userId + "&levelId=" + moduleId);
        Pattern pattern = Pattern.compile("aria-controls=\"#course-([0-9]+)\">\\s*([a-zA-Z\\-0-9\\s+]+)");
        Matcher matcher = pattern.matcher(coursesPartialHtml);
        Pair pair = null;
        List<Pair> coursesInEnrolledModule = new ArrayList<>();
        while (matcher.find()) {
            pair = new Pair(matcher.group(2).trim(), matcher.group(1));
            coursesInEnrolledModule.add(pair);
        }
        return coursesInEnrolledModule;
    }

    private List<Pair> getCourseInstancesIdsInEnrolledCourse(String userId, String enrolledCourseId) throws IOException {
        String courseInstancesPartialHtml = this.sendRequest(String.format("https://softuni.bg/users/trainings/getcourseinstancesincourse?userId=%s&courseId=%s", userId, enrolledCourseId));
        Pattern pattern = Pattern.compile("aria-controls=\"#course-instance-([0-9]+)\">\\s*([a-zA-Z\\-0-9\\s+]+)");
        Matcher matcher = pattern.matcher(courseInstancesPartialHtml);
        Pair pair = null;
        List<Pair> coursesInstancesInEnrolledCourse = new ArrayList<>();
        while (matcher.find()) {
            pair = new Pair(matcher.group(2).trim(), matcher.group(1));
            coursesInstancesInEnrolledCourse.add(pair);
        }
        return coursesInstancesInEnrolledCourse;
    }

    private HashMap<String, Integer> getCoursesInstancesIdsInEnrolledCourses(String userId, HashMap<String, Integer> coursesAndCourseIds) throws IOException {
        HashMap<String, Integer> courseInstancesInEnrolledCourse = new HashMap<>();
        for (String course: coursesAndCourseIds.keySet()) {
            List<Pair> courseAndEnrolledCourseInstanceInModule = this.getCourseInstancesIdsInEnrolledCourse(userId, coursesAndCourseIds.get(course).toString());
            courseAndEnrolledCourseInstanceInModule.stream().forEach(pair -> courseInstancesInEnrolledCourse
                    .put(pair.getKey().toString(),
                            Integer.parseInt(pair.getValue().toString())));
        }
        return courseInstancesInEnrolledCourse;
    }
}
