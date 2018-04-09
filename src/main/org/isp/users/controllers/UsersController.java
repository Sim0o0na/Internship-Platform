package org.isp.users.controllers;

import org.isp.users.models.dtos.UserDto;
import org.isp.users.models.dtos.UserEditDto;
import org.isp.users.models.dtos.UserRegisterDto;
import org.isp.base.services.api.ImageService;
import org.isp.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

@Controller
public class UsersController {
    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/login")
    public String getLoginPage(@ModelAttribute UserRegisterDto userRegisterDto, Model model) {
        model.addAttribute("userDto" , new UserRegisterDto());
        return "users/login";
    }

    @GetMapping("/register")
    public String getRegisterPage(@ModelAttribute UserRegisterDto userRegisterDto, Model model) {
        return "users/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute UserRegisterDto userDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/users/register";
        }

        this.userService.register(userDto);
        return "redirect:/login";
    }

    @GetMapping("/profile/{username}")
    public String profile(@PathVariable(name = "username") String username, Model model, Principal principal)
            throws Exception {

        if(!username.equals(principal.getName())) {
            return "redirect:/dashboard";
        }
        UserEditDto userDto = (UserEditDto) this.userService.findByUsername(principal.getName(), UserEditDto.class);
        model.addAttribute("userDto", userDto);
        model.addAttribute("user", principal.getName());
        return "users/profile";
    }

    @PostMapping("/edit/{username}")
    public String editProfile(UserEditDto userEditDto,
                              @PathVariable(value="username") String username,
                              BindingResult bindingResult) {
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
