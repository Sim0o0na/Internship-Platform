package org.isp.users.controllers;

import org.isp.users.models.dtos.UserChangePasswordDto;
import org.isp.users.models.dtos.UserEditDto;
import org.isp.users.models.dtos.UserRegisterDto;
import org.isp.base.services.api.ImageService;
import org.isp.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.UUID;

@Controller
public class UserController {
    private UserService userService;
    private ImageService imageService;

    @Autowired
    public UserController(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
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

    @GetMapping("/profile/{username}")
    public String profile(@PathVariable(name = "username") String username,
                          @RequestParam(value = "info", defaultValue = "") String info,
                          Model model, Principal principal,
                          RedirectAttributes redirectAttributes)
            throws Exception {
        if (principal == null) {
            redirectAttributes.addAttribute("showloginform", true);
            return "redirect:/";
        }
        else if(!username.equals(principal.getName())) {
            return "redirect:/dashboard";
        }
        UserEditDto userDto = (UserEditDto) this.userService.findByUsername(principal.getName(), UserEditDto.class);
        if (!info.equals("")) {
            model.addAttribute("info", info);
        }
        model.addAttribute("userDto", userDto);
        model.addAttribute("user", principal.getName());
        return "users/profile";
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

    @PostMapping("/edit/{username}")
    public String editProfile(UserEditDto userEditDto,
                              @PathVariable(value="username") String username,
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
        this.userService.edit(username, userEditDto);
        return "redirect:/profile/" + username;
    }
}
