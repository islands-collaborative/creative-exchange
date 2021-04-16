package com.islandcollaborative.creativeexchange.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

public class AuthController {

    @GetMapping("/login")
    public String showLogin(){
        return "login";
    }

    @GetMapping("/signup")
    public String showSignup(){
        return "sign-up";
    }

    // POST /signup: Creates a new user when they sign up. Redirects user to user account page /users/
    @PostMapping("/signup")
    public RedirectView createUser (){
        return new RedirectView("");
    }
}
