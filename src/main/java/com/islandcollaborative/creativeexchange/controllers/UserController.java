package com.islandcollaborative.creativeexchange.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {
    @GetMapping("/discover")
    public String getUsers(){
        return "";
    }

    @GetMapping("/users/{id}")
    public String getUserById (@PathVariable long id){
        return "";
    }

    // Provides a view that allows users to edit their own profile, edit and add their posts.
    @GetMapping("/profile")
    public String getProfile(){
        return "";
    }

    // PUT /profile: Requires authentication. Allows a user to edit the details of their own profile, including changing their bio, information, linked accounts. Redirects to /profile.
    @PutMapping("/profile")
    public String updateProfile(){
        return "";
    }
}
