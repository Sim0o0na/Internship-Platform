package org.isp.domain.notifications;

import java.util.Arrays;

public enum NotificationType {
    SUCCESS, INFO, WARNING, ERROR;
    public static String[] types(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }
}
