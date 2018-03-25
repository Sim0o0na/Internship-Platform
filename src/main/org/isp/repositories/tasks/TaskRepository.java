package org.isp.repositories.tasks;

import org.isp.model.entity.tasks.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findByAssigneeUsername(String assigneeUsername);

    @Query("select t from Task t where t.id not in " +
            "(select task.id from TaskApplication ta where ta.user.username = :assigneeUsername)")
    List<Task> findAllByAssigneeUsernameNotLike(@Param(value = "assigneeUsername")
                                                        String assigneeUsername);
}
