package org.isp.tasks.services;

import org.isp.notifications.api.NotificationService;
import org.isp.notifications.entity.NotificationType;
import org.isp.tasks.models.dtos.TaskCreateDto;
import org.isp.tasks.models.dtos.TaskDto;
import org.isp.tasks.models.entities.Task;
import org.isp.users.models.entities.User;
import org.isp.tasks.repositories.TaskRepository;
import org.isp.users.repositories.UserRepository;
import org.isp.util.DateFormatUtil;
import org.isp.util.MappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private NotificationService notificationService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,
                           UserRepository userRepository,
                           NotificationService notificationService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @Override
    public void create(TaskCreateDto taskDto) throws ParseException {
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDueDate(DateFormatUtil.parseDate(taskDto.getDueDate()));
        task.setDescription(taskDto.getDescription());
        task.getPayment().setCost(taskDto.getPaymentCost());
        task.setType(taskDto.getType());
        this.taskRepository.saveAndFlush(task);
        this.notificationService.createForAllUsers(String.format("A new task has been opened: \"%s\"!", task.getTitle()), NotificationType.NEW_TASK);
    }

    @Override
    public Page<TaskDto> fetchAllTasks(Pageable pageable) {
        Page<Task> allTasks = this.taskRepository.findAllByOrderByDueDateAsc(pageable);
        Page<TaskDto> dtos = MappingUtil.convert(allTasks, TaskDto.class);
        return dtos;
    }

    @Override
    public Page<TaskDto> fetchTasksForUser(Pageable pageable, String username) {
        Page<Task> allTasksByAssignee = this.taskRepository.findByAssigneeUsernameOrderByDueDateAsc(pageable, username);
        Page<TaskDto> dtos = MappingUtil.convert(allTasksByAssignee, TaskDto.class);
        return dtos;
    }

    @Override
    public Page<TaskDto> fetchNonAppliedTasks(Pageable pageable, String username) {
        Page<Task> nonAppliedTasks = this.taskRepository.findAllByAssigneeUsernameNotLike(username, pageable);
        Page<TaskDto> dtos = MappingUtil.convert(nonAppliedTasks, TaskDto.class);
        return dtos;
    }

    @Override
    public TaskDto getMostRecentTaskByUser(String username) {
        Task mostRecentTask = this.taskRepository.findFirstByAssigneeUsernameOrderByDueDateAsc(username);
        if (mostRecentTask == null) {
            throw new IllegalArgumentException("There are currently no assigned tasks!");
        }
        TaskDto dto = MappingUtil.convert(mostRecentTask, TaskDto.class);
        return dto;
    }

    @Override
    public void edit(String taskId, TaskDto taskDto) throws IllegalAccessException, ParseException {
        Task task = this.taskRepository.getOne(taskId);
        task.setTitle(taskDto.getTitle());
        task.setDueDate(taskDto.getDueDate());
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
    public Task findTaskById(String taskId) {
        Task task = this.taskRepository.getOne(taskId);
        return task;
    }

    @Override
    public void assignTaskToUser(String taskId, String username) throws Exception {
        Task task = this.taskRepository.getOne(taskId);
        User user = this.userRepository.findByUsername(username);
        task.setAssignee(user);
        task.setCompleted(false);
        this.taskRepository.saveAndFlush(task);
    }

    @Override
    public Page<TaskDto> searchTasks(String dateFrom, String dateTo, String assigneeUsername, Pageable pageable) throws ParseException {
        Page<Task> foundTasks;
        if(dateFrom.isEmpty() && dateTo.isEmpty()) {
            foundTasks = this.taskRepository.findByAssigneeUsernameOrderByDueDateAsc(pageable, assigneeUsername);
        } else {
            Date from = DateFormatUtil.parseDate(dateFrom);
            Date to = DateFormatUtil.parseDate(dateTo);
            foundTasks = this.taskRepository.findAllByDueDateBetweenOrAssigneeUsername(from, to, assigneeUsername, pageable);
        }

        return MappingUtil.convert(foundTasks, TaskDto.class);
    }

    @Override
    public void completeTask(String taskId) {
        Optional<Task> task = this.taskRepository.findById(taskId);
        if(task.isPresent()) {
           task.get().setCompleted(true);
           task.get().getPayment().setActive(true);
           this.taskRepository.saveAndFlush(task.get());
        }
    }
}
