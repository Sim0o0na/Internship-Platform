package org.isp.applications.training_details.parser;

import java.io.IOException;
import java.util.HashMap;

public interface UserInfoParser<K, V> {
    HashMap<K, V> getInfo(String username, String targetInfo) throws IOException;
}
