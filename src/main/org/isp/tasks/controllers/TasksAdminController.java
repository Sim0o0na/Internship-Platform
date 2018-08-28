package org.isp.tasks.controllers;

import org.isp.tasks.models.TaskType;
import org.isp.tasks.models.dtos.TaskCreateDto;
import org.isp.tasks.models.dtos.TaskDto;
import org.isp.payments.services.PaymentService;
import org.isp.tasks.services.TaskService;
import org.isp.users.services.UserService;
import org.isp.util.ControllerUtil;
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

    @Autowired
    public TasksAdminController(TaskService taskService, UserService userService, PaymentService paymentService) {
        this.taskService = taskService;
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
        }
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
