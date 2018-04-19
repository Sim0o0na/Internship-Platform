package org.isp.tasks.services;

import org.isp.tasks.models.dtos.TaskDto;
import org.isp.tasks.models.entities.Task;
import org.isp.tasks.models.entities.TaskApplication;
import org.isp.users.models.entities.User;

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
