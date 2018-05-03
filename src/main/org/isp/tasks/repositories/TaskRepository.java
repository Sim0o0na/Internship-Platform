package org.isp.tasks.repositories;

import org.isp.tasks.models.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    Page<Task> findByAssigneeUsernameOrderByDueDateAsc(Pageable pageable, String assigneeUsername);

    Page<Task> findAllByOrderByDueDateAsc(Pageable pageable);

    Task findFirstByAssigneeUsernameOrderByDueDateAsc(String assigneeUsername);

    @Query("select t from Task t where t.id not in " +
            "(select task.id from TaskApplication ta where ta.user.username = :assigneeUsername) order by t.dueDate desc")
    Page<Task> findAllByAssigneeUsernameNotLike(@Param(value = "assigneeUsername")
                                                        String assigneeUsername, Pageable pageable);

    Page<Task> findAllByDueDateBetweenOrAssigneeUsername(Date dateFrom, Date dateTo, String assigneeUsername, Pageable pageable);
}
