package org.isp.tasks.controllers;

import org.isp.tasks.models.dtos.TaskDto;
import org.isp.tasks.services.TaskApplicationService;
import org.isp.tasks.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;

@Controller
@RequestMapping("/tasks")
public class TasksController {
    private TaskService taskService;

    @Autowired
    private TaskApplicationService taskApplicationService;

    @Autowired
    public TasksController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/apply/{taskId}")
    private String applyTask(@PathVariable(value = "taskId") String taskId, Model model) {
        TaskDto dto = this.taskService.findById(taskId);
        System.out.println("apply get!");
        model.addAttribute("task", dto);
        return "/tasks/apply-task";
    }

    @PostMapping("/apply/{taskId}")
    private String apply(@PathVariable(value = "taskId") String taskId, Principal principal) throws IOException {
        TaskDto taskDto = this.taskService.findById(taskId);
        if (taskDto == null) {
            return "redirect:/dashboard";
        }
        this.taskApplicationService.createTaskApplication();
        return "redirect:/dashboard";
    };

    @RequestMapping(value = "/tasks/all", method = RequestMethod.GET)
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
}
