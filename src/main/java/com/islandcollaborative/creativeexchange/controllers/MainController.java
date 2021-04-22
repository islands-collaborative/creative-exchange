package com.islandcollaborative.creativeexchange.controllers;

import com.islandcollaborative.creativeexchange.models.AppUser;
import com.islandcollaborative.creativeexchange.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    AppUserRepository appUserRepository;

    @GetMapping("/")
    public String getIndex(Model m) {
        m.addAttribute("creators", appUserRepository.findByIsCreatorTrue());
        return "index";
    }

    @GetMapping("/about")
    public String getAbout() {
        return "about-us";
    }

    @GetMapping("/privacy")
    public String getPrivacy() {
        return "privacy";
    }
}
