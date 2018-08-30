package org.isp.domain.applications.training_details;

public class TrainingCourseDto {
    private String courseName;
    private String grade;

    public TrainingCourseDto() {
    }

    public TrainingCourseDto(String name, String grade) {
        this.courseName = name;
        this.grade = grade;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String name) {
        this.courseName = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
