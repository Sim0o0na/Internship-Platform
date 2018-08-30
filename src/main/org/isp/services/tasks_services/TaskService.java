package org.isp.services.tasks_services;

import org.isp.domain.tasks.dtos.TaskCreateDto;
import org.isp.domain.tasks.dtos.TaskDto;

import org.isp.domain.tasks.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;

public interface TaskService {
    void create(TaskCreateDto taskDto) throws ParseException;

    Page<TaskDto> fetchAllTasks(Pageable pageable);

    Page<TaskDto> fetchTasksForUser(Pageable pageable, String username);

    Page<TaskDto> fetchNonAppliedTasks(Pageable pageable, String username);

    TaskDto getMostRecentTaskByUser(String username);

    void edit(String taskId, TaskDto taskEditDto) throws IllegalAccessException, ParseException;

    TaskDto findById(String taskId);

    Task findTaskById(String taskId);

    void assignTaskToUser(String taskId, String username) throws Exception;

    Page<TaskDto> searchTasks(String dateFrom, String dateTo, String assigneeUsername, Pageable pageable) throws ParseException;

    void completeTask(String taskId);
}
