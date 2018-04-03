package org.isp.services.api;

import org.isp.model.dto.TaskDto;
import org.isp.model.entity.tasks.TaskApplication;

import java.io.IOException;
import java.util.List;

public interface TaskApplicationService {
    List<TaskApplication> fetchAll();

    List<TaskApplication> fetchAllNonAssigned();

    void applyUserToTask(String username, TaskDto taskDto) throws IOException;
}
