package org.isp.controllers;

import org.isp.model.dto.TaskDto;
import org.isp.model.dto.UserAdminViewDto;
import org.isp.model.dto.UserDto;
import org.isp.model.entity.tasks.TaskApplication;
import org.isp.services.api.TaskService;
import org.isp.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    private String admin(Model model){
        List<TaskDto> allTasks = this.taskService.fetchAllTasks();
        model.addAttribute("tasks", allTasks);
        return "admin/admin.html";
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    private String getTasksPanel(Model model) {
        List<TaskDto> allTasks = this.taskService.fetchAllTasks();
        model.addAttribute("tasks", allTasks);
        return "admin/tasks/tasks-panel";
    }

    @RequestMapping(value = "/tasks/all", method = RequestMethod.GET)
    private String allTasks(Model model){
        List<TaskDto> allTasks = this.taskService.fetchAllTasks();
        model.addAttribute("tasks", allTasks);
        return "admin/tasks/all-tasks-partial";
    }

//    @RequestMapping(value = "/tasks/applications", method = RequestMethod.GET)
//    private String allTaskApplications(Model model){
//        List<TaskApplication> allTaskApplications = this.taskService.fetchAllTasks();
//        model.addAttribute("tasks", allTasks);
//        return "admin/tasks/all-tasks-partial";
//    }

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

    @RequestMapping(value = "/tasks/edit/{taskId}", method = RequestMethod.GET)
    private String edit(@PathVariable(value = "taskId") String taskId, Model model) {
        TaskDto dto = this.taskService.findById(taskId);
        model.addAttribute("task", dto);
        model.addAttribute("taskId", dto.getId());
        return "admin/tasks/edit-task";
    }

    @RequestMapping(value = "/users/all", method = RequestMethod.GET)
    private String allUsers(Model model){
        List<UserAdminViewDto> allUsers = this.userService.fetchAllUsers();
        model.addAttribute("users", allUsers);
        return "admin/users/users-panel";
    }

    @RequestMapping(value = "/users/search/{username}", method = RequestMethod.POST)
    private String searchUser(@PathVariable(value = "username") String username, Model model)
            throws Exception {
        UserAdminViewDto found = (UserAdminViewDto) this.userService
                .findByUsername(username, UserAdminViewDto.class);
        model.addAttribute("result", found);
        return "admin/users/user-result-partial";
    }
}
