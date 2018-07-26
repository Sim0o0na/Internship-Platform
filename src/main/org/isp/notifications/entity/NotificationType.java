package org.isp.notifications.entity;

import java.util.Arrays;

public enum NotificationType {
    PAYMENT, ALERT, INFO, NEW_TASK;
    public static String[] types(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }
}
