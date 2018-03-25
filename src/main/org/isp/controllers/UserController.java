package org.isp.controllers;

import org.isp.model.dto.UserDto;
import org.isp.model.dto.UserEditDto;
import org.isp.model.dto.UserRegisterDto;
import org.isp.services.api.ImageService;
import org.isp.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.UUID;

@Controller
public class UserController {
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

    @GetMapping("/profile/{username}")
    public String profile(@PathVariable(name = "username") String username, Model model, Principal principal)
            throws Exception {

        if(!username.equals(principal.getName())) {
            return "redirect:/dashboard";
        }
        UserEditDto userDto = (UserEditDto) this.userService.findByUsername(principal.getName(), UserDto.class);
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

    public MultipartFile getProfilePhoto() {
        
    }
}
