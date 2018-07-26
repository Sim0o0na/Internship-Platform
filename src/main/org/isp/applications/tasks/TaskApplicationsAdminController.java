package org.isp.applications.tasks;

import org.isp.tasks.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/tasks/applications")
public class TaskApplicationsAdminController {
    private TaskApplicationService taskApplicationService;
    private TaskService taskService;

    @Autowired
    public TaskApplicationsAdminController(TaskApplicationService taskApplicationService, TaskService taskService) {
        this.taskApplicationService = taskApplicationService;
        this.taskService = taskService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    private String taskApplicationsPanel(Model model){
        return "admin/tasks/task-applications-panel-partial";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    private String allTaskApplications(Model model){
        List<TaskApplication> allTaskApplications = this.taskApplicationService.fetchAllNonAssigned();
        model.addAttribute("taskApplications", allTaskApplications);
        return "admin/tasks/all-task-applications-partial";
    }

    @RequestMapping(value = "/approve", method = RequestMethod.GET)
    private String approveTaskApplication(@RequestParam(value="taskId", required=false) String taskId,
                                          @RequestParam(value="user", required=false) String username) throws Exception {
        this.taskService.assignTaskToUser(taskId, username);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/decline", method = RequestMethod.GET)
    private String declineTaskApplication(@RequestParam(value="taskId", required=false) String taskId) throws Exception {
        this.taskApplicationService.declineTaskApplication(taskId);
        return "redirect:/admin";
    }
}
