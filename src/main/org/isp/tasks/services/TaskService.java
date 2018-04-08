package org.isp.tasks.services;

import org.isp.tasks.models.dtos.TaskDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.text.ParseException;
import java.util.List;

public interface TaskService {
    void create(TaskDto taskDto) throws ParseException;

    Page<TaskDto> fetchAllTasks(Pageable pageable);

    List<TaskDto> fetchTasksForUser(String username);

    List<TaskDto> fetchNonAppliedTasks(String username);

    void edit(String taskId, TaskDto taskEditDto) throws IllegalAccessException, ParseException;

    TaskDto findById(String taskId);

    void assignTaskToUser(String taskId, String username) throws Exception;
}
