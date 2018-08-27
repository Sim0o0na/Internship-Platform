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
    public HashMap<String, String> getInfo(String username) throws IOException {
        String userDetailsHtml;
        try {
            userDetailsHtml = HTTPRequestSender.sendRequest("https://softuni.bg/users/profile/show/" + username);
        } catch (Exception e) {
            throw new IllegalArgumentException("Username does not exist!");
        }
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(userDetailsHtml);
        String userEmail = "";
        if (matcher.find()) {
            userEmail = matcher.group(1);
        }
        HashMap<String, String> result = new HashMap<>();
        result.put("email", userEmail);
        return result;
    }
}
