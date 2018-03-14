package org.isp.services.api;

import org.isp.model.dto.TaskDto;

import java.text.ParseException;
import java.util.List;

public interface TaskService {
    void create(TaskDto taskDto) throws ParseException;

    List<TaskDto> fetchAllTasks();

    List<TaskDto> fetchTasksForUser(String username);
}
