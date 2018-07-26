package org.isp.tasks.repositories;

import org.isp.applications.tasks.TaskApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TaskApplicationRepository extends JpaRepository<TaskApplication, String> {
    @Query("select ta from TaskApplication ta where ta.task.assignee is null")
    List<TaskApplication> findAllByNonAssignedTask();

    @Modifying
    @Transactional
    @Query("update TaskApplication ta set ta.isDeclined = true where ta.id = :taskApplicationId")
    void declineById(@Param("taskApplicationId") String taskApplicationId);

    TaskApplication findByUserUsernameAndTaskId(String userId, String taskId);
}
