package org.isp.applications.training_details.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTrainingCourseDetailsRepository extends JpaRepository<UserTrainingCourseDetails, Long> {
    UserTrainingCourseDetails findByTrainingCourseCourseName(String courseName);
    List<UserTrainingCourseDetails> findAllByUsername(String username);
}
