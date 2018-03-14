package org.isp.controllers;

import org.isp.model.dto.TaskDto;
import org.isp.services.api.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private TaskService taskService;

    @Autowired
    public HomeController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String index(Model model, Principal principal) throws IOException {
        if (principal != null) {
           this.dashboard(principal, model);
        }
        return "home";
    }

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) throws IOException {
        List<TaskDto> tasks = this.taskService.fetchAllTasks();
        model.addAttribute("user", principal.getName());
        model.addAttribute("tasks" , tasks);
        return "dashboard";
    }

    @GetMapping("/about")
    public String getAboutPage(){
        return "about";
    }
}
