package org.isp.web.controllers;

import org.isp.services.notifications_services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class HomeController {
    private NotificationService notificationService;

    @Autowired
    public HomeController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/")
    public String index(Principal principal) {
        if (principal != null) {
           return "redirect:/dashboard";
        }
        return "home";
    }

    @GetMapping("/projects")
    public String projectsPage() {
        return "projects";
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard(Principal principal, Model model) {
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
    public String getApplyPage(@RequestParam(value = "info", defaultValue = "") String info, Model model){
        if (!info.equals("")) {
            model.addAttribute("info", info);
        }
        return "apply";
    }
}
