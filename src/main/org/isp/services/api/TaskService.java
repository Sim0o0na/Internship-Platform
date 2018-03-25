package org.isp.services.api;

import org.isp.model.dto.TaskDto;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface TaskService {
    void create(TaskDto taskDto) throws ParseException;

    List<TaskDto> fetchAllTasks();

    List<TaskDto> fetchTasksForUser(String username);

    List<TaskDto> fetchNonAppliedTasks(String username);

    void edit(String taskId, TaskDto taskEditDto) throws IllegalAccessException, ParseException;

    TaskDto findById(String taskId);

    void applyUserToTask(String username, TaskDto taskDto) throws IOException;
}
