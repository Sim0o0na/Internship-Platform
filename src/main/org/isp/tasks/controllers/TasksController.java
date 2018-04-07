package org.isp.tasks.controllers;

import org.isp.tasks.models.dtos.TaskDto;
import org.isp.services.api.TaskApplicationService;
import org.isp.tasks.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;

@Controller
public class TasksController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskApplicationService taskApplicationService;

    @GetMapping("/tasks/apply/{taskId}")
    private String applyTask(@PathVariable(value = "taskId") String taskId, Model model) {
        TaskDto dto = this.taskService.findById(taskId);
        System.out.println("apply get!");
        model.addAttribute("task", dto);
        return "/tasks/apply-task";
    }

    @PostMapping("/tasks/apply/{taskId}")
    private String apply(@PathVariable(value = "taskId") String taskId, Principal principal) throws IOException {
        TaskDto taskDto = this.taskService.findById(taskId);
        if (taskDto == null) {
            return "redirect:/dashboard";
        }
        this.taskApplicationService.applyUserToTask(principal.getName(), taskDto);
        return "redirect:/dashboard";
    };

    @PostMapping("/admin/tasks/edit/{taskId}")
    private String edit(TaskDto taskEditDto,
                        BindingResult bindingResult,
                        @PathVariable(value = "taskId") String taskId) throws ParseException, IllegalAccessException {
        if (bindingResult.hasErrors()) {
            return "/admin/tasks/all";
        }
        this.taskService.edit(taskId, taskEditDto);
        return "redirect:/admin/tasks/all";
    }
}