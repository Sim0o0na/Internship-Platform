package org.isp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
    private static BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public PasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        PasswordEncoder.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public static String encodePassword(String pass) {
        if(pass == null || pass.isEmpty()) {
            throw new IllegalArgumentException("Cannot encode empty password!");
        }
        return bCryptPasswordEncoder.encode(pass);
    }

    public boolean checkPassword(String pass, String encoded) {
        return bCryptPasswordEncoder.matches(pass, encoded);
    }
}
