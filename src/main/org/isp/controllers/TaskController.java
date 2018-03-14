package org.isp.controllers;

import org.isp.model.dto.TaskDto;
import org.isp.services.api.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/admin/tasks/create")
    private String createTask(@ModelAttribute TaskDto taskCreateDto,
                           BindingResult bindingResult) throws ParseException {
        this.taskService.create(taskCreateDto);
        return "redirect:/admin";
    }

    @GetMapping("/admin/tasks/create")
    private String createPanel(Model model) {
        model.addAttribute("taskDto", new TaskDto());
        return "/admin/create-task";
    }

    @PostMapping("/dashboard/tasks/apply/{taskId}")
    private String apply(@RequestParam String taskId) {return null;};

}
