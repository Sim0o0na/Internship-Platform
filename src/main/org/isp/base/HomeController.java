package org.isp.base;

import org.isp.notifications.api.NotificationService;
import org.isp.tasks.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;

@Controller
public class HomeController {
    private TaskService taskService;
    private NotificationService notificationService;

    @Autowired
    public HomeController(TaskService taskService, NotificationService notificationService) {
        this.taskService = taskService;
        this.notificationService = notificationService;
    }

    @GetMapping("/")
    public String index(Model model, Principal principal) throws IOException {
        if (principal != null) {
           return "redirect:/dashboard";
        }
        return "home";
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard(Principal principal, Model model) throws IOException {
        ModelAndView modelAndView = new ModelAndView("dashboard");
        try {
            modelAndView.getModel().putIfAbsent("user", principal.getName());
            modelAndView.getModel().putIfAbsent("notifications", this.notificationService.getAllNotReadForUser(principal.getName()));
        } catch (IllegalArgumentException iae) {
            model.addAttribute("error", iae.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/about")
    public String getAboutPage(){
        return "about";
    }

    @GetMapping("/apply")
    public String getApplyPage(){
        return "apply";
    }
}
