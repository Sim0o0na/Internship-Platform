package org.isp.web.controllers.applications_controllers;

import org.isp.services.user_services.user_application_services.UserApplicationService;
import org.isp.domain.applications.user_applications.UserApplicationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

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
            redirectAttributes.addFlashAttribute("error", "User application with this username or email already exists!");
            return "redirect:/apply";
        }
        try {
            this.userApplicationService.create(userApplicationDto);
            CompletableFuture result = this.userDetailsController.createUserTrainingDetails(userApplicationDto.getUsername());
            if (result.isDone()) {
                this.userDetailsController.addTrainingDetailsToUserApplication(userApplicationDto.getUsername());
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/apply";
        }
        redirectAttributes.addFlashAttribute("info", "You have successfully applied for the internship program!");
        return "redirect:/";
    }
}