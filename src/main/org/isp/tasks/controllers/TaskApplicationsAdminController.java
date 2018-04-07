package org.isp.tasks.controllers;

import org.isp.tasks.models.entities.TaskApplication;
import org.isp.services.api.TaskApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/admin/tasks/applications")
public class TaskApplicationsAdminController {
    private TaskApplicationService taskApplicationService;

    @Autowired
    public TaskApplicationsAdminController(TaskApplicationService taskApplicationService) {
        this.taskApplicationService = taskApplicationService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    private String allTaskApplications(Model model){
        List<TaskApplication> allTaskApplications = this.taskApplicationService.fetchAllNonAssigned();
        model.addAttribute("taskApplications", allTaskApplications);
        return "admin/tasks/all-task-applications-partial";
    }
}
