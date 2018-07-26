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
import java.util.HashMap;
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
        HashMap<String, Integer> modulesAndModulesIds = this.getTrainingModulesForUser(username);
        HashMap<String, Integer> coursesAndCourseInstancesIds = new HashMap<>();
        for (String module: modulesAndModulesIds.keySet()) {
            String moduleId = modulesAndModulesIds.get(module).toString();
            Pair courseAndEnrolledCourseInModule = this.getCourseInstancesAndIds(userId, moduleId);
            coursesAndCourseInstancesIds.putIfAbsent(
                    courseAndEnrolledCourseInModule.getKey().toString(),
                    Integer.parseInt(courseAndEnrolledCourseInModule.getValue().toString()));
        }
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

    private HashMap<String, Integer> getTrainingModulesForUser(String username) throws IOException {
        String userTrainingsPageHtml = this.sendRequest("https://softuni.bg/users/profile/trainings/" + username);
        Pattern pattern = Pattern.compile("aria-controls=\"#module-([1-9]+)\">\\s*([a-zA-Z\\s(]+)");
        Matcher matcher = pattern.matcher(userTrainingsPageHtml);
        HashMap<String, Integer> result = new HashMap<>();
        while (matcher.find()){
            result.putIfAbsent(matcher.group(2).trim(), Integer.parseInt(matcher.group(1)));
        }
        return result;
    }

    private Pair getCourseInstancesAndIds(String userId, String moduleId) throws IOException {
        String courseInstancesPartialHtml = this.sendRequest("https://softuni.bg/users/trainings/getcoursesinenrolledlevel?userId="
                + userId + "&levelId=" + moduleId);
        Pattern pattern = Pattern.compile("aria-controls=\"#course-([0-9]+)\">\\s*([a-zA-Z\\-0-9\\s+]+)");
        Matcher matcher = pattern.matcher(courseInstancesPartialHtml);
        Pair pair = null;
        if(matcher.find()) {
            pair = new Pair(matcher.group(2), matcher.group(1));
        }
        return pair;
    }
}
