package org.isp.repositories.tasks;

import org.isp.model.entity.tasks.TaskApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskApplicationRepository extends JpaRepository<TaskApplication, String> {
}
