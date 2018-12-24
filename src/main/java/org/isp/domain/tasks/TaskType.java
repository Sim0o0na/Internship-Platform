package org.isp.domain.tasks;

import java.util.Arrays;

public enum TaskType {
    MENTOR, INVIGILATOR, LECTURER, ASSESSMENT, EXAM, RESOURCES;
    public static String[] types(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }
}
