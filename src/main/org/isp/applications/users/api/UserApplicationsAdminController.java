package org.isp.applications.users.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
        ModelAndView modelAndView = new ModelAndView("/admin/users/user-applications", new ModelMap());
        modelAndView.getModel().put("applications", this.userApplicationService.getAllNotApproved());
        return modelAndView;
    }

    @RequestMapping(value = "/approve/{id}", method = RequestMethod.POST)
    public void approve(@PathVariable Long id) {
        this.userApplicationService.approve(id);
    }
}
