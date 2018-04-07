package org.isp.tasks.services;

import org.isp.tasks.models.dtos.TaskDto;

import java.text.ParseException;
import java.util.List;

public interface TaskService {
    void create(TaskDto taskDto) throws ParseException;

    List<TaskDto> fetchAllTasks();

    List<TaskDto> fetchTasksForUser(String username);

    List<TaskDto> fetchNonAppliedTasks(String username);

    void edit(String taskId, TaskDto taskEditDto) throws IllegalAccessException, ParseException;

    TaskDto findById(String taskId);

    void assignTaskToUser(String taskId, String username) throws Exception;
}
