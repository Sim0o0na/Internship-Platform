package org.isp.services.impl;

import org.isp.model.dto.TaskDto;
import org.isp.model.entity.tasks.Task;
import org.isp.repositories.tasks.TaskRepository;
import org.isp.services.api.TaskService;
import org.isp.util.MappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void create(TaskDto taskDto) throws ParseException {
        SimpleDateFormat dtf = new SimpleDateFormat("YYYY-MM-dd");
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDueDate(dtf.parse(taskDto.getDueDate()));
        task.setDescription(taskDto.getDescription());
        this.taskRepository.saveAndFlush(task);
    }

    @Override
    public List<TaskDto> fetchAllTasks() {
        List<Task> allTasks = this.taskRepository.findAll();
        List<TaskDto> dtos = MappingUtil.convert(allTasks, TaskDto.class);
        return dtos;
    }

    @Override
    public List<TaskDto> fetchTasksForUser(String username) {
        List<Task> allTasksByAssignee = this.taskRepository.findByAssigneeUsername(username);
        List<TaskDto> dtos = MappingUtil.convert(allTasksByAssignee, TaskDto.class);
        return dtos;
    }
}
