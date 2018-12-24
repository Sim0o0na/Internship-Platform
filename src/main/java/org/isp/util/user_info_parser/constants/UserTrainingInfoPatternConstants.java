package org.isp.util.user_info_parser.constants;

public final class UserTrainingInfoPatternConstants {
    public static final String USER_ID_PATTERN = "for=\"Id\">Id<\\/label>\\s*([a-zA-Z0-9-]+)\\s*<\\/div>";
    public static final String ENROLLED_MODULES_PATTERN = "aria-controls=\"#module-([0-9]+)\">\\s*([a-zA-Z\\#\\s(]+)";
    public static final String COURSES_IN_ENROLLED_MODULE_PATTERN = "aria-controls=\"#course-([0-9]+)\">\\s*([a-zA-Z\\-0-9\\s\\#+]+)";
    public static final String COURSE_ISTANCES_IN_ENROLLED_COURSE_PATTERN = "aria-controls=\"#course-instance-([0-9]+)\">\\s*([a-zA-Z\\#\\-0-9\\s+]+)";
    public static final String ENROLLED_TRAINING_DETAILS_PATTERN = "ОЦЕНКА:\\s*<strong>(.*)[(]([0-9.]+)[)](.*)<\\/strong>";
    public static final String NO_ENROLLED_TRAININGS_BG_STRING = "НЯМА ЗАПИСАНИ МОДУЛИ.";
}
