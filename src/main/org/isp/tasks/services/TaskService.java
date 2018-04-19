package org.isp.tasks.services;

import org.isp.tasks.models.dtos.TaskCreateDto;
import org.isp.tasks.models.dtos.TaskDto;

import org.isp.tasks.models.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.text.ParseException;
import java.util.List;

public interface TaskService {
    void create(TaskCreateDto taskDto) throws ParseException;

    Page<TaskDto> fetchAllTasks(Pageable pageable);

    Page<TaskDto> fetchTasksForUser(Pageable pageable, String username);

    Page<TaskDto> fetchNonAppliedTasks(Pageable pageable, String username);

    TaskDto getMostRecentTaskByUser(String username);

    void edit(String taskId, TaskDto taskEditDto) throws IllegalAccessException, ParseException;

    TaskDto findById(String taskId);

    void assignTaskToUser(String taskId, String username) throws Exception;
}
