package org.isp.services.api;

import org.isp.tasks.models.dtos.TaskDto;
import org.isp.tasks.models.entities.TaskApplication;

import java.io.IOException;
import java.util.List;

public interface TaskApplicationService {
    List<TaskApplication> fetchAll();

    List<TaskApplication> fetchAllNonAssigned();

    void applyUserToTask(String username, TaskDto taskDto) throws IOException;
}
