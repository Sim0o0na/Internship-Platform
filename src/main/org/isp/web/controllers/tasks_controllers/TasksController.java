package org.isp.web.controllers.tasks_controllers;

import org.isp.domain.tasks.dtos.TaskDto;
import org.isp.domain.tasks.entities.Task;
import org.isp.services.tasks_services.task_applications_services.TaskApplicationService;
import org.isp.services.tasks_services.TaskService;
import org.isp.domain.users.entities.User;
import org.isp.services.user_services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/tasks")
public class TasksController {
    private TaskService taskService;
    private UserService userService;

    @Autowired
    private TaskApplicationService taskApplicationService;

    @Autowired
    public TasksController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/apply/{taskId}")
    private String applyTask(@PathVariable(value = "taskId") String taskId, Model model) {
        TaskDto dto = this.taskService.findById(taskId);
        model.addAttribute("task", dto);
        return "/tasks/apply-task";
    }

    @PostMapping("/apply/{taskId}")
    private String apply(@PathVariable(value = "taskId") String taskId, Principal principal) throws IOException {
        Task task = this.taskService.findTaskById(taskId);
        User user = this.userService.findByUsername(principal.getName());
        if (task == null) {
            return "redirect:/dashboard";
        }
        this.taskApplicationService.createTaskApplication(task, user);
        return "redirect:/tasks/all?user=&partial=false";
    };

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    private String allTasks(@RequestParam(value = "user") String username,
                                  @RequestParam(value = "partial") boolean isPartial,
                                  Model model,
                                  Principal principal,
                                  @PageableDefault(size = 4) Pageable pageable) {
        String view = "";
        Page<TaskDto> tasks;
        if (!username.equals("")) {
            tasks = this.taskService.fetchTasksForUser(pageable, principal.getName());
            if (isPartial) {
                view = "/users/user-tasks-partial";
            } else {
                view = "/users/user-tasks";
            }
        } else {
            tasks = this.taskService.fetchNonAppliedTasks(pageable, principal.getName());
            if (isPartial) {
                view = "/tasks/all-tasks-partial";
            } else {
                view = "/tasks/all-tasks";
            }
        }

        model.addAttribute("pagesCount", tasks.getTotalPages());
        model.addAttribute("tasks", tasks.getContent());
        model.addAttribute("user", principal.getName());
        return view;
    }

    @RequestMapping(value = "/complete/{taskId}", method = RequestMethod.GET)
    private ModelAndView complete(@PathVariable(name = "taskId") String taskId) {
        this.taskService.completeTask(taskId);
        return new ModelAndView("/users/user-tasks");
    }
}
