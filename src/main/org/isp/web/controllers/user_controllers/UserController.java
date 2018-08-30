package org.isp.web.controllers.user_controllers;

import org.isp.domain.applications.training_details.UserTrainingCourseDetails;
import org.isp.domain.users.dtos.UserChangePasswordDto;
import org.isp.domain.users.dtos.UserEditDto;
import org.isp.domain.users.dtos.UserRegisterDto;
import org.isp.services.images_services.api.ImageService;
import org.isp.services.training_details_services.UserTrainingDetailsService;
import org.isp.services.user_services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.UUID;

@Controller
public class UserController {
    private UserService userService;
    private ImageService imageService;
    private UserTrainingDetailsService userTrainingDetailsService;

    @Autowired
    public UserController(UserService userService,
                          ImageService imageService,
                          UserTrainingDetailsService userTrainingDetailsService) {
        this.userService = userService;
        this.imageService = imageService;
        this.userTrainingDetailsService = userTrainingDetailsService;
    }

    @GetMapping("/login")
    public String getLoginPage(@ModelAttribute UserRegisterDto userRegisterDto, Model model) {
        model.addAttribute("userDto" , new UserRegisterDto());
        return "/users/login-form";
    }

//    @GetMapping("/register")
//    public String getRegisterPage(@ModelAttribute UserRegisterDto userRegisterDto, Model model) {
//        return "users/register";
//    }
//
//    @PostMapping("/register")
//    public String registerUser(@Valid @ModelAttribute UserRegisterDto userDto, BindingResult bindingResult){
//        if(bindingResult.hasErrors()){
//            return "/users/register";
//        }
//
//        this.userService.register(userDto);
//        return "redirect:/login";
//    }

    @GetMapping("/profile")
    public String profile(Principal principal, RedirectAttributes redirectAttributes, Model model) throws Exception {
        if (principal == null) {
            redirectAttributes.addAttribute("showloginform", true);
            return "redirect:/";
        }
        return "redirect:/profile/" + principal.getName();
    }

    @GetMapping("/profile/{username}")
    public String profile(@PathVariable(name = "username") String username,
                          @RequestParam(value = "info", defaultValue = "") String info,
                          Model model, Principal principal,
                          RedirectAttributes redirectAttributes)
            throws Exception {
        if (principal == null) {
            redirectAttributes.addAttribute("showloginform", true);
            return "redirect:/";
        } else if (!username.equals(principal.getName())) {
            return "redirect:/dashboard";
        }
        UserEditDto userDto = (UserEditDto) this.userService.findByUsername(principal.getName(), UserEditDto.class);
        if (!info.equals("")) {
            model.addAttribute("info", info);
        }
        model.addAttribute("userDto", userDto);
        model.addAttribute("userCourses", this.userTrainingDetailsService.getCourseDetailsForUsername(username));
        model.addAttribute("averageGrade", String.format("%.2f", this.userTrainingDetailsService.getAverageGradeForUsername(username)));
        return "/users/profile";
    }

    @GetMapping("/profile/changepassword")
    public String changePassword() {
        return "/users/password-change-form";
    }

    @PostMapping("/profile/changepassword")
    public String changePassword(Principal principal, UserChangePasswordDto dto, RedirectAttributes redirectAttrs) {
        try {
            this.userService.changeUserPassword(principal.getName(), dto);
        } catch (Exception e) {
            redirectAttrs.addAttribute("error", e.getMessage());
        }
        redirectAttrs.addFlashAttribute("info", "Successfully changed password!");
        return "redirect:/profile/" + principal.getName();
    }

    @GetMapping("/profile/edit")
    public String editForm(Model model, Principal principal) throws Exception {
        UserEditDto userDto = (UserEditDto) this.userService.findByUsername(principal.getName(), UserEditDto.class);
        model.addAttribute("userDto", userDto);
        return "/users/user-edit-form";
    }

    @PostMapping("/edit")
    public String editProfile(UserEditDto userEditDto,
                              Principal principal,
                              BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "/users/register-form";
        }

        try {
            String photoName = UUID.randomUUID().toString();
            this.imageService.store(userEditDto.getProfilePhoto(), photoName);
            userEditDto.setProfilePhotoLocation(photoName);
        } catch (Exception e) {
            System.out.println("could not upload profile photo");
        }
        this.userService.edit(principal.getName(), userEditDto);
        return "redirect:/profile/" + principal.getName();
    }
}
