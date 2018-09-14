package org.isp.web.controllers;

import org.isp.web.controllers.applications_controllers.UserApplicationAdminController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserApplicationAdminController userApplicationAdminController;

    @Autowired
    public AdminController(UserApplicationAdminController userApplicationAdminController) {
        this.userApplicationAdminController = userApplicationAdminController;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    private String base(Model model) {
//        int waitingApplicationsCount = this.userApplicationAdminController.getAllWaitingCount();
//        model.addAttribute("applicationsCount", waitingApplicationsCount);
        return "admin/admin-base";
    }
}
