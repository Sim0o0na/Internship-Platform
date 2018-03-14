package org.isp.repositories.tasks;

import org.isp.model.entity.tasks.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findByAssigneeUsername(String assigneeUsername);
}
