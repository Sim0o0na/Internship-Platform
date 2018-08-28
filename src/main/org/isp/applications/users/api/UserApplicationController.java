package org.isp.applications.users.api;

import org.isp.applications.training_details.controller.UserTrainingDetailsController;
import org.isp.applications.users.entity.UserApplicationDto;
import org.isp.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users/applications")
public class UserApplicationController {
    private UserApplicationService userApplicationService;
    private UserTrainingDetailsController userDetailsController;

    @Autowired
    public UserApplicationController(UserApplicationService userApplicationService,
                                     UserTrainingDetailsController userDetailsController) {
        this.userApplicationService = userApplicationService;
        this.userDetailsController = userDetailsController;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute UserApplicationDto userApplicationDto,
                         RedirectAttributes redirectAttributes) {
        if (this.userApplicationService.checkIfExists(userApplicationDto)) {
            redirectAttributes.addFlashAttribute("info", "User application with this username or email already exists!");
            return "redirect:/apply";
        }
        try {
            this.userApplicationService.create(userApplicationDto);
            this.userDetailsController.createUserTrainingDetails(userApplicationDto.getUsername());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("info", e.getMessage());
            return "redirect:/apply";
        }
        redirectAttributes.addFlashAttribute("info", "You have successfully applied for the internship program!");
        return "redirect:/";
    }
}