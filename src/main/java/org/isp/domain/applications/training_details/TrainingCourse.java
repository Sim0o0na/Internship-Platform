package org.isp.domain.applications.training_details;

import javax.persistence.*;

@Entity
@Table(name = "training_courses")
public class TrainingCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_name", unique = true, nullable = false)
    private String courseName;

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
}
