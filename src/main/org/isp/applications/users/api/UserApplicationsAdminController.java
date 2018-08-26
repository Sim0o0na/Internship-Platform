package org.isp.applications.users.api;

import org.isp.applications.training_details.entity.UserTrainingCourseDetails;
import org.isp.applications.users.entity.UserApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/admin/users/applications")
public class UserApplicationsAdminController {
    private UserApplicationService userApplicationService;

    @Autowired
    public UserApplicationsAdminController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView approve() {
        ModelAndView modelAndView = new ModelAndView("/admin/users/applications/user-applications", new ModelMap());
        modelAndView.getModel().put("applications", this.userApplicationService.getAllNotApproved());
        return modelAndView;
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String getApplicationForUser(@PathVariable(value = "username") String username, Model model) {
        UserApplication userApplication = this.userApplicationService.getByUsername(username);
        model.addAttribute("userApplication", userApplication);
        model.addAttribute("averageGrade", userApplication.getUserTrainingDetails().getAverageGrade());
        //this.getCoursesAndGrades(userApplication.getUserTrainingDetails().getUserCoursesDetails())
        model.addAttribute("coursesDetails", new HashMap<>());
        return "/admin/users/applications/user-applications-view-more";
    }

    private HashMap<String, Double> getCoursesAndGrades(List<UserTrainingCourseDetails> coursesDetails) {
        HashMap<String, Double> result = new HashMap<>();
        for (UserTrainingCourseDetails courseDetails : coursesDetails) {
            result.put(courseDetails.getTrainingCourse().getCourseName(), courseDetails.getGrade());
        }
        return result;
    }

    @RequestMapping(value = "/approve/{id}", method = RequestMethod.POST)
    public void approve(@PathVariable Long id) {
        this.userApplicationService.approve(id);
    }
}
