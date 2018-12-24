package org.isp.web.controllers.applications_controllers;

import org.isp.domain.applications.training_details.TrainingCourseDto;
import org.isp.services.training_details_services.UserTrainingDetailsService;
import org.isp.services.user_services.user_application_services.UserApplicationService;
import org.isp.domain.applications.user_applications.UserApplication;
import org.isp.domain.applications.user_applications.UserApplicationStatus;
import org.isp.services.user_services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/admin/users/applications")
public class UserApplicationAdminController {
    private static final String APPROVED_USER_APPLICATION_STRING = "User application for username \"%s\" approved!";
    private static final String REJECTED_USER_APPLICATION_STRING = "User application for username \"%s\" rejected!";

    private UserApplicationService userApplicationService;
    private UserTrainingDetailsService userTrainingDetailsService;
    private UserService userService;

    @Autowired
    public UserApplicationAdminController(UserApplicationService userApplicationService,
                                          UserTrainingDetailsService userTrainingDetailsService,
                                          UserService userService) {
        this.userApplicationService = userApplicationService;
        this.userTrainingDetailsService = userTrainingDetailsService;
        this.userService = userService;
    }

    @RequestMapping(value = "/allwaiting", method = RequestMethod.GET)
    public ModelAndView getAllWaiting() {
        ModelAndView modelAndView = new ModelAndView("/admin/users/applications/user-applications", new ModelMap());
        modelAndView.getModel().put("applications", this.userApplicationService.getAllNotApproved());
        return modelAndView;
    }

    @RequestMapping(value = "/approve", method = RequestMethod.GET)
    public String approve(@RequestParam(value="username", required=true) String username,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        try {
            this.userApplicationService.setStatus(username, UserApplicationStatus.APPROVED);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        this.userService.createUser(this.userApplicationService.getByUsername(username));
        model.addAttribute("info", String.format(APPROVED_USER_APPLICATION_STRING, username));
        redirectAttributes.addAttribute("showpanel", "user-applications");
        return "redirect:/admin";
    }

    @RequestMapping(value = "/reject", method = RequestMethod.GET)
    public String reject(@RequestParam(value="username", required=true) String username, Model model) {
        try {
            this.userApplicationService.setStatus(username, UserApplicationStatus.REJECTED);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("info", String.format(REJECTED_USER_APPLICATION_STRING, username));
        return "/admin/admin-base";
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String getApplicationForUsername(@PathVariable(value = "username") String username, Model model) throws IOException {
        UserApplication userApplication = this.userApplicationService.getByUsername(username);
        model.addAttribute("userApplication", userApplication);
        List<TrainingCourseDto> courseDetails = this.userTrainingDetailsService.getCourseDetailsForUsername(username);
        if (courseDetails.size() == 0) {
            courseDetails = new ArrayList<>();
        }
        if (userApplication.getUserTrainingDetails() != null) {
            model.addAttribute("averageGrade", String.format("%.2f",userApplication.getUserTrainingDetails().getAverageGrade()));
        }
        model.addAttribute("coursesDetails", courseDetails);
        return "/admin/users/applications/user-applications-view-more";
    }
}
