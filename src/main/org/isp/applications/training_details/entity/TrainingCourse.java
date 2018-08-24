package org.isp.applications.training_details.entity;

import javax.persistence.*;

@Entity
@Table(name = "training_courses")
public class TrainingCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_name")
    private String courseName;

    @OneToOne(targetEntity = UserTrainingCourseDetails.class)
    private UserTrainingCourseDetails userTrainingCourseDetails;

    public TrainingCourse() {
    }

    public TrainingCourse(String courseName) {
        this.courseName = courseName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public UserTrainingCourseDetails getUserTrainingCourseDetails() {
        return userTrainingCourseDetails;
    }

    public void setUserTrainingCourseDetails(UserTrainingCourseDetails userTrainingCourseDetails) {
        this.userTrainingCourseDetails = userTrainingCourseDetails;
    }
}
