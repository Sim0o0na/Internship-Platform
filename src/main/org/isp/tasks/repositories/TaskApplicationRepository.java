package org.isp.tasks.repositories;

import org.isp.tasks.models.entities.TaskApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskApplicationRepository extends JpaRepository<TaskApplication, String> {
    @Query("select ta from TaskApplication ta where ta.task.assignee is null")
    List<TaskApplication> findAllByNonAssignedTask();
}
