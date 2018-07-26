package org.isp.applications.users.api;

import org.isp.applications.users.entity.UserApplication;
import org.isp.applications.users.entity.UserApplicationDto;
import org.isp.applications.users.parser.UserInfoController;
import org.isp.users.repositories.UserRepository;
import org.isp.util.ControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/user-applications")
public class UserApplicationController {
    private UserRepository userRepository;
    private UserApplicationService userApplicationService;
    private UserInfoController userInfoController;

    @Autowired
    public UserApplicationController(UserRepository userRepository,
                                     UserApplicationService userApplicationService,
                                     UserInfoController userInfoController) {
        this.userRepository = userRepository;
        this.userApplicationService = userApplicationService;
        this.userInfoController = userInfoController;
    }

    @RequestMapping(value = "/approve/{id}", method = RequestMethod.POST)
    public void approve(@PathVariable Long id) {
        this.userApplicationService.approve(id);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@Valid @ModelAttribute UserApplicationDto userApplicationDto) throws Exception {
        String view = "home";
        ModelAndView modelAndView = new ModelAndView();
        try {
            this.userApplicationService.create(userApplicationDto);
            modelAndView.getModel().put("info", "Вие успешно кандидатствахте за стажантската програма на СофтУни! Очаквайте отговор съвсем скоро!");
        } catch (Exception e) {
            modelAndView.getModel().put("error", e.getMessage());
            view = "/apply";
        }
        modelAndView.setViewName(view);
            this.userInfoController.getUserInfo(userApplicationDto.getUsername());
        return modelAndView;
    }
}
