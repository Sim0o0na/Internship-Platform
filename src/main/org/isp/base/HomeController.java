package org.isp.base;

import org.isp.tasks.models.dtos.TaskDto;
import org.isp.tasks.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.security.Principal;

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
           return "redirect:/dashboard";
        }
        return "home";
    }

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) throws IOException {
        try {
            TaskDto mostRecentTaskByUser = this.taskService.getMostRecentTaskByUser(principal.getName());
            model.addAttribute("mostRecentTask", mostRecentTaskByUser);
            model.addAttribute("user", principal.getName());
        } catch (IllegalArgumentException iae) {
            model.addAttribute("error", iae.getMessage());
        }
        return "dashboard";
    }

    @GetMapping("/about")
    public String getAboutPage(){
        return "about";
    }
}
