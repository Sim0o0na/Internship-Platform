package org.isp.controllers;

import org.isp.model.dto.UserEditDto;
import org.isp.model.dto.UserRegisterDto;
import org.isp.services.api.ImageService;
import org.isp.services.api.StorageService;
import org.isp.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/register")
    public String getRegisterPage(@ModelAttribute UserRegisterDto userRegisterDto, Model model) {
        model.addAttribute("userDto" , new UserRegisterDto());
        return "users/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute UserRegisterDto userRegisterDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/users/register";
        }

        this.userService.register(userRegisterDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "/users/login";
    }

    @GetMapping("/profile/{username}")
    public String profile(@PathVariable(name = "username") String username, Model model, Principal principal)
            throws IOException {

        if(!username.equals(principal.getName())) {
            return "dashboard";
        }
        UserEditDto userDto = this.userService.findByUsername(principal.getName());
        model.addAttribute("userDto", userDto);
        model.addAttribute("user", principal.getName());
        return "users/profile";
    }

    @PostMapping("/edit/{username}")
    public String editProfile(UserEditDto userEditDto,
                              @PathVariable(value="username") String username,
                              BindingResult bindingResult,
                              Principal principal) {
        if(bindingResult.hasErrors()){
            return "users/register";
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
