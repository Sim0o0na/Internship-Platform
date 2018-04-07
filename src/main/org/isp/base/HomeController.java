package org.isp.base;

import org.isp.tasks.models.dtos.TaskDto;
import org.isp.tasks.services.TaskService;
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
           return "redirect:/dashboard";
        }
        return "home";
    }

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) throws IOException {
        List<TaskDto> tasks = this.taskService.fetchTasksForUser(principal.getName());
        if (tasks.size() > 0){
            model.addAttribute("mostRecentTask", tasks.get(0));
        }
        model.addAttribute("tasks" , tasks);
        model.addAttribute("user", principal.getName());
        return "dashboard";
    }

    @GetMapping("/about")
    public String getAboutPage(){
        return "about";
    }
}
