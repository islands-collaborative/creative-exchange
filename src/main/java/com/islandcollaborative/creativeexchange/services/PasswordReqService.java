package com.islandcollaborative.creativeexchange.services;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PasswordReqService {
    public final Map<String, String> errorText = Map.ofEntries(
            Map.entry("password_length", "Password must be at list 6 characters"),
            Map.entry("password_invalid", "Password must contain a lowercase letter, an uppercase letter, and a number")
    );

    public String validate(String password) {
        if (password.length() < 6) return "password_length";
        if (!password.matches(".*[a-zA-Z].*") && password.matches(".*[0-9].*"))
            return "password_invalid";
        else return null;
    }
}
