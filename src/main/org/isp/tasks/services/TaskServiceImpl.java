package org.isp.tasks.services;

import org.isp.tasks.models.dtos.TaskCreateDto;
import org.isp.tasks.models.dtos.TaskDto;
import org.isp.tasks.models.entities.Task;
import org.isp.users.models.entities.User;
import org.isp.tasks.repositories.TaskRepository;
import org.isp.users.repositories.UserRepository;
import org.isp.util.MappingUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,
                           UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public void create(TaskCreateDto taskDto) throws ParseException {
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDueDate(sdf.parse(taskDto.getDueDate()));
        task.setDescription(taskDto.getDescription());
        task.getPayment().setCost(taskDto.getPaymentCost());
        task.setType(taskDto.getType());
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

    private Page<TaskDto> mapTasksToPageDTO(Page<Task> tasks, String username) {
        List<TaskDto> dtos = new ArrayList<>();
        for (Task task : tasks) {
            TaskDto dto = MappingUtil.convert(task, TaskDto.class);
            dto.setAppliedByUser(task.getTaskApplications().stream()
                    .filter(t -> t.getUser().getUsername().equals(username)).collect(Collectors.toList()).size() != 0);
            dtos.add(dto);
        }
        return new PageImpl<>(dtos);
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
