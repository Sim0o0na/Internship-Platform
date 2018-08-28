package org.isp.base;

import org.isp.applications.users.api.UserApplicationAdminController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

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
        int waitingApplicationsCount = this.userApplicationAdminController.getAllWaitingCount();
        model.addAttribute("applicationsCount", waitingApplicationsCount);
        return "admin/admin-base";
    }
}
