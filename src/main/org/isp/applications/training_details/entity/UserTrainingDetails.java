package org.isp.applications.training_details.entity;

import org.isp.applications.users.entity.UserApplication;

import javax.persistence.*;

@Entity
@Table(name = "user_training_details")
public class UserTrainingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    private double averageGrade;

    @OneToOne(mappedBy = "userTrainingDetails")
    private UserApplication userApplication;

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

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public UserApplication getUserApplication() {
        return userApplication;
    }

    public void setUserApplication(UserApplication userApplication) {
        this.userApplication = userApplication;
    }
}
