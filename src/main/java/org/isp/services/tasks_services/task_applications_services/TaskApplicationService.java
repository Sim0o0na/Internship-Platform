package org.isp.services.tasks_services.task_applications_services;

import org.isp.domain.applications.TaskApplication;
import org.isp.domain.tasks.dtos.TaskDto;
import org.isp.domain.tasks.entities.Task;
import org.isp.domain.users.entities.User;

import java.io.IOException;
import java.util.List;

public interface TaskApplicationService {
    List<TaskApplication> fetchAll();

    List<TaskApplication> fetchAllNonAssigned();

    void createTaskApplication(Task task, User user);

    void apply(String username, TaskDto taskDto) throws IOException;

    void approveTaskApplication(String taskApplicationId) throws Exception;

    void declineTaskApplication(String taskApplicationId);
}
