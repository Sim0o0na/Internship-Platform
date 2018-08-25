package org.isp.applications.training_details.entity;

import org.isp.applications.users.entity.UserApplication;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_training_details")
public class UserTrainingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;

    @OneToMany(mappedBy = "userTrainingDetails", targetEntity = UserTrainingCourseDetails.class, fetch = FetchType.EAGER)
    private List<UserTrainingCourseDetails> userCoursesDetails;

    private double averageGrade;

    public UserTrainingDetails() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<UserTrainingCourseDetails> getUserCoursesDetails() {
        return userCoursesDetails;
    }

    public void setUserCoursesDetails(List<UserTrainingCourseDetails> userCoursesDetails) {
        this.userCoursesDetails = userCoursesDetails;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }
}
