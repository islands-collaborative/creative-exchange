package com.islandcollaborative.creativeexchange.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String getIndex() {
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
