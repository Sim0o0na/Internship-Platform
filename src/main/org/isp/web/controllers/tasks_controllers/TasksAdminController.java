package org.isp.web.controllers.tasks_controllers;

import org.isp.domain.notifications.NotificationType;
import org.isp.domain.tasks.TaskType;
import org.isp.domain.tasks.dtos.TaskCreateDto;
import org.isp.domain.tasks.dtos.TaskDto;
import org.isp.services.notifications_services.NotificationService;
import org.isp.services.payment_services.PaymentService;
import org.isp.services.tasks_services.TaskService;
import org.isp.services.user_services.UserService;
import org.isp.util.ControllerUtil;
import org.isp.web.PublisherEventSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;

@Controller
@RequestMapping("/admin/tasks")
public class TasksAdminController {
    private TaskService taskService;
    private NotificationService notificationService;

    @Autowired
    public TasksAdminController(TaskService taskService,
                                NotificationService notificationService) {
        this.taskService = taskService;
        this.notificationService = notificationService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    private String tasksPanel(Model model){
        return "admin/tasks/tasks-panel";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    private String allTasksPanelPartial(Model model, @PageableDefault(size = 4) Pageable pageable){
        Page<TaskDto> allTasks = this.taskService.fetchAllTasks(pageable);
        model.addAttribute("tasks", allTasks.getContent());
        model.addAttribute("pagesCount", allTasks.getTotalPages());
        return "admin/tasks/all-tasks-partial";
    }

    @RequestMapping(value = "/edit/{taskId}", method = RequestMethod.GET)
    private String edit(@PathVariable(value = "taskId") String taskId, Model model) {
        TaskDto dto = this.taskService.findById(taskId);
        model.addAttribute("task", dto);
        model.addAttribute("taskId", dto.getId());
        return "admin/tasks/edit-task";
    }

    @RequestMapping(value = "/edit/{taskId}", method = RequestMethod.POST)
    private String edit(TaskDto taskEditDto,
                        BindingResult bindingResult,
                        @PathVariable(value = "taskId") String taskId) throws ParseException, IllegalAccessException {
        if (bindingResult.hasErrors()) {
            return "/admin/tasks/all";
        }
        this.taskService.edit(taskId, taskEditDto);
        return "redirect:/admin/tasks/all";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    private String createPanel(Model model) {
        model.addAttribute("taskTypes", TaskType.types(TaskType.class));
        model.addAttribute("taskDto", new TaskCreateDto());
        return "/admin/tasks/create-task-form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    private String createTask(@Valid @ModelAttribute TaskCreateDto taskCreateDto,
                              BindingResult bindingResult,
                              Model model) throws ParseException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "There was an error creating this task!");
        } else {
            this.taskService.create(taskCreateDto);
            this.notificationService.createForAllUsers(String.format("A new task \"%s\" is opened", taskCreateDto.getTitle()),
                    NotificationType.INFO);
        }
        PublisherEventSubscriber.triggerEvent(String.format("A new task \"%s\" is opened", taskCreateDto.getTitle()));
        return "admin/admin-base";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    private String searchTasks(@RequestParam(value = "dateFrom", required = false) String dateFrom,
                               @RequestParam(value = "dateTo", required = false) String dateTo,
                               @RequestParam(value = "assignee", required = false) String assigneeUsername,
                               Model model,
                               Pageable pageable) throws ParseException {
        Page<TaskDto> foundTasks;
        try {
            foundTasks = this.taskService.searchTasks(dateFrom, dateTo, assigneeUsername, pageable);
        } catch (IllegalArgumentException iae) {
            ControllerUtil.addErrorToModel(iae.getMessage(), model);
            return "admin/tasks/all-tasks-partial";
        }

        model.addAttribute("tasks", foundTasks);
        model.addAttribute("pagesCount", foundTasks.getTotalPages());
        return "admin/tasks/all-tasks-partial";
    }
}
