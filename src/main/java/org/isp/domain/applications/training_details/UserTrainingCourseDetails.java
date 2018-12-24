package org.isp.domain.applications.training_details;

import javax.persistence.*;

@Entity
@Table(name = "user_courses_details")
public class UserTrainingCourseDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_grade")
    private Double grade;

    @ManyToOne(targetEntity = TrainingCourse.class, cascade = CascadeType.PERSIST)
    private TrainingCourse trainingCourse;

    @Column(name = "softuni_username", nullable = false)
    private String username;

    public UserTrainingCourseDetails() {}

    public UserTrainingCourseDetails(TrainingCourse course, String userGrade, String username) {
        this.trainingCourse = course;
        this.grade = Double.parseDouble(userGrade);
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TrainingCourse getTrainingCourse() {
        return trainingCourse;
    }

    public void setTrainingCourse(TrainingCourse trainingCourse) {
        this.trainingCourse = trainingCourse;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
