package org.isp.base;

import org.isp.applications.users.api.UserApplicationAdminController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserApplicationAdminController userApplicationAdminController;

    @Autowired
    public AdminController(UserApplicationAdminController userApplicationAdminController) {
        this.userApplicationAdminController = userApplicationAdminController;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    private ModelAndView base() {
        int waitingApplicationsCount = this.userApplicationAdminController.getAllWaitingCount();
        ModelAndView modelAndView = new ModelAndView("admin/admin-base", new ModelMap());
        modelAndView.getModel().put("applicationsCount", waitingApplicationsCount);
        return modelAndView;
    }
}
