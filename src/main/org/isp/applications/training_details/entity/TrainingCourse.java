package org.isp.applications.training_details.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "training_courses")
public class TrainingCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_name")
    private String courseName;

    @OneToMany(mappedBy = "trainingCourse")
    private List<UserTrainingCourseDetails> trainingCourseDetails;

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

    public List<UserTrainingCourseDetails> getTrainingCourseDetails() {
        return trainingCourseDetails;
    }

    public void setTrainingCourseDetails(List<UserTrainingCourseDetails> trainingCourseDetails) {
        this.trainingCourseDetails = trainingCourseDetails;
    }
}
