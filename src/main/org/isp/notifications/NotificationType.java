package org.isp.notifications;

import java.util.Arrays;

public enum NotificationType {
    PAYMENT, NEW_TASK, INFO;
    public static String[] types(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }
}
