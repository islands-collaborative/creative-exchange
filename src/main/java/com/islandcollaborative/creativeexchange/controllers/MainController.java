package com.islandcollaborative.creativeexchange.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public class MainController {

    @GetMapping("/")
    public String getIndex() {
        return "";
    }

    @GetMapping("/about")
    public String getAbout() {
        return "";
    }

    @GetMapping("/privacy")
    public String getPrivacy() {
        return "";
    }
}
