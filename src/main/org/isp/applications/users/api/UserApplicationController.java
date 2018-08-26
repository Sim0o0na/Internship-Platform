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
    public ModelAndView create(@Valid @ModelAttribute UserApplicationDto userApplicationDto) throws Exception {
        String view = "home";
        ModelAndView modelAndView = new ModelAndView(view, new ModelMap());
        if (this.userApplicationService.checkIfExists(userApplicationDto)) {
            modelAndView.getModel().put("error", "User application with this username or email already exists!");
            modelAndView.setViewName("apply");
            return modelAndView;
        }
        try {
            this.userApplicationService.create(userApplicationDto);
            this.userDetailsController.createUserTrainingDetails(userApplicationDto.getUsername());
            modelAndView.getModel().put("info", "Вие успешно кандидатствахте за стажантската програма на СофтУни! Очаквайте отговор съвсем скоро!");
        } catch (Exception e) {
            modelAndView.getModel().put("error", e.getMessage());
            view = "apply";
        }
        modelAndView.setViewName(view);
        return modelAndView;
    }
}
