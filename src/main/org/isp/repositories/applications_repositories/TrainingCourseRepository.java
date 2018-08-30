package org.isp.repositories.applications_repositories;

import org.isp.domain.applications.training_details.TrainingCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingCourseRepository extends JpaRepository<TrainingCourse, Long> {
    TrainingCourse findByCourseName(String courseName);
}
