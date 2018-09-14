package org.isp.util.user_info_parser;

import org.isp.util.user_info_parser.constants.UserInfoURLConstants;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component(value = "personalInfoParser")
public class UserPersonalInfoParser implements UserInfoParser<String, String> {
    private static final String EMAIL_PATTERN = "class=\"email lighter\">\\s*([a-zA-Z0-9_]+@([a-zA-Z0-9.]+)+)\\s*<";

    @Override
    public HashMap<String, String> getInfo(String username, String targetInfo) throws IOException {
        String userDetailsHtml;
        try {
            userDetailsHtml = SoftUniHTTPRequestSender.sendRequest(String.format(UserInfoURLConstants.USER_PROFILE_URL));
        } catch (Exception e) {
            throw new IllegalArgumentException("Username does not exist!");
        }

        HashMap<String, String> result = new HashMap<>();
        switch (targetInfo.toLowerCase()) {
            case "email":;
                result = this.getUserEmail(userDetailsHtml);
            break;
        }

        return result;
    }

    private HashMap<String, String> getUserEmail(String html) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(html);
        String parsedResult = "";
        int counter = 0;
        while (counter < 2) {
            if (matcher.find()) {
                parsedResult = matcher.group(1);
            }
            counter++;
        }
        HashMap<String, String> result = new HashMap<>();
        result.put("email", parsedResult);
        return result;
    }

    @Override
    public boolean isInfoAvailable(String username) throws IOException {
        return false;
    }
}
