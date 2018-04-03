package org.isp.services.impl;

import org.isp.model.dto.TaskDto;
import org.isp.model.entity.tasks.Task;
import org.isp.model.entity.tasks.TaskApplication;
import org.isp.model.entity.users.User;
import org.isp.repositories.tasks.TaskApplicationRepository;
import org.isp.repositories.tasks.TaskRepository;
import org.isp.repositories.user.UserRepository;
import org.isp.services.api.TaskService;
import org.isp.services.api.UserService;
import org.isp.util.MappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private TaskApplicationRepository taskApplicationRepository;
    private UserRepository userRepository;
    private final SimpleDateFormat dtf;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,
                           TaskApplicationRepository taskApplicationRepository,
                           UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskApplicationRepository = taskApplicationRepository;
        this.userRepository = userRepository;
        this.dtf = new SimpleDateFormat("YYYY-MM-dd");
    }

    @Override
    public void create(TaskDto taskDto) throws ParseException {
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

    @Override
    public List<TaskDto> fetchNonAppliedTasks(String username) {
        List<Task> nonAppliedTasks = this.taskRepository.findAllByAssigneeUsernameNotLike(username);
        List<TaskDto> dtos = MappingUtil.convert(nonAppliedTasks, TaskDto.class);
        return dtos;
    }

    @Override
    public void edit(String taskId, TaskDto taskDto) throws IllegalAccessException, ParseException {
        Task task = this.taskRepository.getOne(taskId);
        task.setTitle(taskDto.getTitle());
        task.setDueDate(dtf.parse(taskDto.getDueDate()));
        task.setDescription(taskDto.getDescription());
        this.taskRepository.saveAndFlush(task);
    }

    @Override
    public TaskDto findById(String taskId) {
        Task task = this.taskRepository.getOne(taskId);
        TaskDto dto = MappingUtil.convert(task, TaskDto.class);
        return dto;
    }

    @Override
    public void assignTaskToUser(String taskId, String username) throws Exception {
        Task task = this.taskRepository.getOne(taskId);
        User user = this.userRepository.findByUsername(username);
        task.setAssignee(user);
        task.setCompleted(false);
        this.taskRepository.saveAndFlush(task);
    }
}
