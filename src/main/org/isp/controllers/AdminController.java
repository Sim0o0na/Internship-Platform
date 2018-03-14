package org.isp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin")
    private String admin(){
        System.out.println("admin here!");
        return "admin/admin";
    }

    @GetMapping("/admin/tasks")
    private String tasksPanel() {
        return "admin/admin-tasks";
    }
}
