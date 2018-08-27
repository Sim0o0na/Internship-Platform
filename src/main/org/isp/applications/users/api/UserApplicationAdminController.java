package org.isp.applications.users.api;

import org.isp.applications.training_details.controller.UserTrainingDetailsController;
import org.isp.applications.training_details.entity.UserTrainingCourseDetails;
import org.isp.applications.users.entity.UserApplication;
import org.isp.applications.users.entity.UserApplicationStatus;
import org.isp.users.controllers.UserController;
import org.isp.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(path = "/admin/users/applications")
public class UserApplicationAdminController {
    private UserApplicationService userApplicationService;
    private UserTrainingDetailsController userTrainingDetailsController;
    private UserService userService;

    @Autowired
    public UserApplicationAdminController(UserApplicationService userApplicationService,
                                          UserTrainingDetailsController userTrainingDetailsController,
                                          UserService userService) {
        this.userApplicationService = userApplicationService;
        this.userTrainingDetailsController = userTrainingDetailsController;
        this.userService = userService;
    }

    @RequestMapping(value = "/allwaiting", method = RequestMethod.GET)
    public ModelAndView getAllWaiting() {
        ModelAndView modelAndView = new ModelAndView("/admin/users/applications/user-applications", new ModelMap());
        modelAndView.getModel().put("applications", this.userApplicationService.getAllNotApproved());
        return modelAndView;
    }

    public int getAllWaitingCount() {
        return this.userApplicationService.getAllNotApproved().size();
    }

    @RequestMapping(value = "/approve", method = RequestMethod.GET)
    public String approve(@RequestParam(value="username", required=true) String username, Model model) {
        try {
            this.userApplicationService.setStatus(username, UserApplicationStatus.APPROVED);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        this.userService.createUser(this.userApplicationService.getByUsername(username));
        model.addAttribute("info", String.format("User application for username \"%s\" approved!", username));
        return "/admin/admin-base";
    }

    @RequestMapping(value = "/reject", method = RequestMethod.GET)
    public String reject(@RequestParam(value="username", required=true) String username, Model model) {
        try {
            this.userApplicationService.setStatus(username, UserApplicationStatus.REJECTED);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("info", String.format("User application for username \"%s\" rejected!", username));
        return "/admin/admin-base";
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String getApplicationForUsername(@PathVariable(value = "username") String username, Model model) throws IOException {
        UserApplication userApplication = this.userApplicationService.getByUsername(username);
        model.addAttribute("userApplication", userApplication);
        model.addAttribute("averageGrade", String.format("%.2f",userApplication.getUserTrainingDetails().getAverageGrade()));
        model.addAttribute("coursesDetails",
                this.getCoursesAndGrades(this.userTrainingDetailsController.getCourseDetailsForUsername(username)));
        return "/admin/users/applications/user-applications-view-more";
    }

    private HashMap<String, Double> getCoursesAndGrades(List<UserTrainingCourseDetails> coursesDetails) {
        HashMap<String, Double> result = new HashMap<>();
        for (UserTrainingCourseDetails courseDetails : coursesDetails) {
            result.put(courseDetails.getTrainingCourse().getCourseName(), courseDetails.getGrade());
        }
        return result;
    }
}
