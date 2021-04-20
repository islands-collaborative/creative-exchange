package com.islandcollaborative.creativeexchange.controllers;

import com.islandcollaborative.creativeexchange.models.AppUser;
import com.islandcollaborative.creativeexchange.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    AppUserRepository appUserRepository;

    /**
     * @return the discover page
     * GET /discover
     * <p>
     * Serves a view of the creators that are showcasing their profiles.
     */
    @GetMapping("/discover")
    public String getUsers() {
        List<AppUser> allUsers = appUserRepository.findAll();
//        to do: finish creator logic and add to discover page
        for (int i = 0; i < allUsers.size(); i++) {
            AppUser user = allUsers.get(i);
//            if (user.getCreator() == true) creators.add(user);
        }
        return "discover";
    }

    /**
     * @return details for a user
     * GET /users
     * Requires authentication
     * <p>
     * Serves the public view of a user's profile. This will include a short bio, profile picture, and a feed
     * of their posts.
     */
    @GetMapping("/users/{userId}")
    public String getUserById(@PathVariable long userId, Model m) {
        AppUser userPrincipal = appUserRepository.getOne(userId);
        m.addAttribute("messages", userPrincipal);
        return "user-detail";
    }

    /**
     * @return editable details for the principal.user
     * GET /profile
     * Requires authentication
     * <p>
     * Provides a view that allows users to edit their own profile, edit and add their posts
     */
    @GetMapping("/profile")
    public String getProfile(HttpServletRequest request, Model m) {
        return "my-profile";
    }

    /**
     * @return redirects to /profile
     * PUT /profile
     * Requires authentication
     * <p>
     * Allows a user to edit the details of their own profile, including changing their bio,
     * information, linked accounts. Redirects to /profile.
     */
    @PutMapping("/profile")
    public RedirectView updateProfile( String displayName,
                                      String bio,
                                      String blurb,
                                      Boolean isCreator,
                                      HttpServletRequest request,
                                      Model m) {
        AppUser userPrincipal = appUserRepository.findByUsername(request.getUserPrincipal().getName());
        if (bio != null) userPrincipal.setBio(bio);
        if (displayName != null) userPrincipal.setDisplayName(displayName);
        if (isCreator != null) userPrincipal.setCreator(isCreator);
        if (blurb != null) userPrincipal.setBlurb(blurb);
        appUserRepository.save(userPrincipal);
        return new RedirectView("profile");
    }
}
