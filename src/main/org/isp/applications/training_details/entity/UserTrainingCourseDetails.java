package org.isp.applications.training_details.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_courses_details")
public class UserTrainingCourseDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "userTrainingCourseDetails")
    private TrainingCourse trainingCourse;

    @Column(name = "user_grade")
    private Double grade;

    @ManyToOne(targetEntity = UserTrainingDetails.class)
    private UserTrainingDetails userTrainingDetails;

    public UserTrainingCourseDetails(TrainingCourse course, Double userGrade, UserTrainingDetails userTrainingDetails) {
        this.trainingCourse = course;
        this.grade = userGrade;
        this.userTrainingDetails = userTrainingDetails;
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

    public UserTrainingDetails getUserTrainingDetails() {
        return userTrainingDetails;
    }

    public void setUserTrainingDetails(UserTrainingDetails userTrainingDetails) {
        this.userTrainingDetails = userTrainingDetails;
    }
}
