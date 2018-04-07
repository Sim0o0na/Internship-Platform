package org.isp.tasks.repositories;

import org.isp.tasks.models.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findByAssigneeUsernameOrderByDueDateDesc(String assigneeUsername);

    @Query("select t from Task t where t.id not in " +
            "(select task.id from TaskApplication ta where ta.user.username = :assigneeUsername) order by t.dueDate desc")
    List<Task> findAllByAssigneeUsernameNotLike(@Param(value = "assigneeUsername")
                                                        String assigneeUsername);
}
