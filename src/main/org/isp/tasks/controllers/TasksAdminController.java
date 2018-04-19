package org.isp.tasks.controllers;

import org.isp.tasks.models.TaskType;
import org.isp.tasks.models.dtos.TaskDto;
import org.isp.payments.services.PaymentService;
import org.isp.tasks.services.TaskService;
import org.isp.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;

@Controller
@RequestMapping("/admin")
public class TasksAdminController {
    private TaskService taskService;

    @Autowired
    public TasksAdminController(TaskService taskService, UserService userService, PaymentService paymentService) {
        this.taskService = taskService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    private String admin(Model model, RedirectAttributes redirectAttributes){
        return "admin/admin-base";
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    private String tasksPanel(@PageableDefault(size = 4) Pageable pageable, Model model) {
        return "admin/tasks/tasks-panel";
    }

    @RequestMapping(value = "/tasks/all", method = RequestMethod.GET)
    private String allTasksPanelPartial(Model model, @PageableDefault(size = 4) Pageable pageable){
        Page<TaskDto> allTasks = this.taskService.fetchAllTasks(pageable);
        model.addAttribute("tasks", allTasks.getContent());
        model.addAttribute("pagesCount", allTasks.getTotalPages());
        return "admin/tasks/all-tasks-partial";
    }


    @RequestMapping(value = "/tasks/create", method = RequestMethod.GET)
    private String createPanel(Model model) {
        model.addAttribute("taskTypes", TaskType.types(TaskType.class));
        model.addAttribute("taskDto", new TaskDto());
        return "/admin/tasks/create-task-form";
    }

    @RequestMapping(value = "/tasks/create", method = RequestMethod.POST)
    private String createTask(@Valid @ModelAttribute TaskDto taskCreateDto,
                              BindingResult bindingResult,
                              Model model) throws ParseException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Kur tate banica");
        } else {
            this.taskService.create(taskCreateDto);
        }
        return "admin/admin-base";
    }

    @RequestMapping(value = "/tasks/approve", method = RequestMethod.GET)
    private String approveTaskApplication(@RequestParam(value="taskId", required=false) String taskId,
                                          @RequestParam(value="user", required=false) String username) throws Exception {
        this.taskService.assignTaskToUser(taskId, username);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/tasks/edit/{taskId}", method = RequestMethod.GET)
    private String edit(@PathVariable(value = "taskId") String taskId, Model model) {
        TaskDto dto = this.taskService.findById(taskId);
        model.addAttribute("task", dto);
        model.addAttribute("taskId", dto.getId());
        return "admin/tasks/edit-task";
    }
}
