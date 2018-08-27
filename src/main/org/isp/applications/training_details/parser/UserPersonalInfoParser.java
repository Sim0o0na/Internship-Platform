package org.isp.applications.training_details.parser;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component(value = "personalInfoParser")
public class UserPersonalInfoParser implements UserInfoParser<String, String> {
    private static final String EMAIL_PATTERN = "class=\"email lighter\">\\s*([a-zA-Z0-9]+@([a-zA-Z0-9.]+)+)\\s*<a";

    @Override
    public HashMap<String, String> getInfo(String username, String targetInfo) throws IOException {
        String userDetailsHtml;
        try {
            userDetailsHtml = HTTPRequestSender.sendRequest("https://softuni.bg/users/profile/show/" + username);
        } catch (Exception e) {
            throw new IllegalArgumentException("Username does not exist!");
        }

        String patternType = "";
        switch (targetInfo.toLowerCase()) {
            case "email":;
                patternType = EMAIL_PATTERN;
            break;
        }
        Pattern pattern = Pattern.compile(patternType);
        Matcher matcher = pattern.matcher(userDetailsHtml);
        String parsedResult = "";
        if (matcher.find()) {
            parsedResult = matcher.group(1);
        }
        HashMap<String, String> result = new HashMap<>();
        result.put(targetInfo, parsedResult);
        return result;
    }
}
