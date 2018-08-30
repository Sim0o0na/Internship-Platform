package org.isp.domain.applications.user_applications;

import java.util.Arrays;

public enum  UserApplicationStatus {
    WAITING, REJECTED, APPROVED;
    public static String[] types(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }
}
