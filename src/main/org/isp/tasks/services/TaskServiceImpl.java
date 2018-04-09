package org.isp.tasks.services;

import org.isp.tasks.models.dtos.TaskDto;
import org.isp.tasks.models.entities.Task;
import org.isp.users.models.entities.User;
import org.isp.tasks.repositories.TaskRepository;
import org.isp.users.repositories.UserRepository;
import org.isp.util.MappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.text.ParseException;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private UserRepository userRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,
                           UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void create(TaskDto taskDto) throws ParseException {
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDueDate(taskDto.getDueDate());
        task.setDescription(taskDto.getDescription());
        task.getPayment().setCost(taskDto.getCost());
        this.taskRepository.saveAndFlush(task);
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
    public List<TaskDto> fetchNonAppliedTasks(String username) {
        List<Task> nonAppliedTasks = this.taskRepository.findAllByAssigneeUsernameNotLike(username);
        List<TaskDto> dtos = MappingUtil.convert(nonAppliedTasks, TaskDto.class);
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
    public void assignTaskToUser(String taskId, String username) throws Exception {
        Task task = this.taskRepository.getOne(taskId);
        User user = this.userRepository.findByUsername(username);
        task.setAssignee(user);
        task.setCompleted(false);
        this.taskRepository.saveAndFlush(task);
    }
}
