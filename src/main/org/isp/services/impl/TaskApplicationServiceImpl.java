package org.isp.services.impl;

import org.isp.tasks.models.dtos.TaskDto;
import org.isp.tasks.models.entities.Task;
import org.isp.tasks.models.entities.TaskApplication;
import org.isp.users.models.entities.User;
import org.isp.repositories.tasks.TaskApplicationRepository;
import org.isp.repositories.tasks.TaskRepository;
import org.isp.services.api.TaskApplicationService;
import org.isp.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class TaskApplicationServiceImpl implements TaskApplicationService {
    private TaskApplicationRepository taskApplicationRepository;
    private UserService userService;
    private TaskRepository taskRepository;

    @Autowired
    public TaskApplicationServiceImpl(TaskApplicationRepository taskApplicationRepository,
                                      UserService userService,
                                      TaskRepository taskRepository) {
        this.taskApplicationRepository = taskApplicationRepository;
        this.userService = userService;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskApplication> fetchAll() {
        return this.taskApplicationRepository.findAll();
    }

    @Override
    public List<TaskApplication> fetchAllNonAssigned(){
        return this.taskApplicationRepository.findAllByNonAssignedTask();
    }

    @Override
    public void applyUserToTask(String username, TaskDto taskDto) throws IOException {
        User user = (User) this.userService.loadUserByUsername(username);
        Task task = this.taskRepository.getOne(taskDto.getId());
        TaskApplication taskApplication = new TaskApplication();
        taskApplication.setUser(user);
        taskApplication.setTask(task);
        this.taskApplicationRepository.saveAndFlush(taskApplication);
    }
}
