package org.isp.tasks.controllers;

import org.isp.tasks.models.dtos.TaskDto;
import org.isp.payments.services.PaymentService;
import org.isp.tasks.services.TaskService;
import org.isp.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.util.List;

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
        List<TaskDto> allTasks = this.taskService.fetchAllTasks();
        model.addAttribute("tasks", allTasks);
        return "admin/admin";
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    private String allTasksPanel(Model model) {
        List<TaskDto> allTasks = this.taskService.fetchAllTasks();
        model.addAttribute("tasks", allTasks);
        return "admin/tasks/tasks-panel";
    }

    @RequestMapping(value = "/tasks/all", method = RequestMethod.GET)
    private String allTasksPanelPartial(Model model){
        List<TaskDto> allTasks = this.taskService.fetchAllTasks();
        model.addAttribute("tasks", allTasks);
        return "admin/tasks/all-tasks-partial";
    }


    @RequestMapping(value = "/tasks/create", method = RequestMethod.GET)
    private String createPanel(Model model) {
        model.addAttribute("taskDto", new TaskDto());
        return "/admin/tasks/create-task-form";
    }

    @RequestMapping(value = "/tasks/create", method = RequestMethod.POST)
    private String createTask(@ModelAttribute TaskDto taskCreateDto,
                              BindingResult bindingResult) throws ParseException {
        this.taskService.create(taskCreateDto);
        return "redirect:/admin";
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
