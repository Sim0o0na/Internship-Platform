package org.isp.web.controllers.user_controllers;

import org.isp.domain.users.dtos.UserAdminViewDto;
import org.isp.services.user_services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class UserAdminController {
    private UserService userService;

    @Autowired
    public UserAdminController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    private String allUsers(Model model){
        List allUsers = this.userService.fetchAllUsers();
        model.addAttribute("users", allUsers);
        return "admin/users/users-panel";
    }

    @RequestMapping(value = "/search/{username}", method = RequestMethod.POST)
    private String searchUser(@PathVariable(value = "username") String username, Model model)
            throws Exception {
        UserAdminViewDto found = (UserAdminViewDto) this.userService.findByUsername(username, UserAdminViewDto.class);
        model.addAttribute("result", found);
        return "admin/users/user-result-partial";
    }
}
