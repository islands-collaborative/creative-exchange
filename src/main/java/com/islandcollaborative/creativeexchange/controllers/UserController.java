package com.islandcollaborative.creativeexchange.controllers;

import com.islandcollaborative.creativeexchange.models.AppUser;
import com.islandcollaborative.creativeexchange.repositories.AppUserRepository;
import com.islandcollaborative.creativeexchange.services.AppUserService;
import com.islandcollaborative.creativeexchange.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    AppUserService appUserService;

    @Autowired
    AppUserRepository appUserRepository;

    /**
     * @return the discover page
     * GET /discover
     * <p>
     * Serves a view of the creators that are showcasing their profiles.
     */
    @GetMapping("/discover")
    public String getUsers(Model m) {
        List<AppUser> creatorUsers = appUserRepository.findByIsCreatorTrue();
        m.addAttribute("users", creatorUsers);
//        to do: finish creator logic and add to discover page

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
        AppUser user = appUserRepository.getOne(userId);
        m.addAttribute("user", user);
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
    public String getProfile() {
        return "my-profile";
    }

    /**
     * @return redirects to /profile
     * PUT /profile/is-creator
     * Requires authentication
     * <p>
     * Allows a user to edit the details of their own profile, including changing their bio,
     * information, linked accounts. Redirects to /profile.
     */
//  // to-do stretch goal refactor to put and delete routes to prevent undesired results
    @PutMapping("/profile/is-creator")
    public RedirectView updateCreatorTrue (HttpServletRequest request){
        AppUser userPrincipal = appUserRepository.findByUsername(request.getUserPrincipal().getName());
        userPrincipal.setCreator(true);
        appUserRepository.save(userPrincipal);
        return new RedirectView("/profile");

    }
    @DeleteMapping("/profile/is-creator")
    public RedirectView updateCreatorFalse (HttpServletRequest request){
        AppUser userPrincipal = appUserRepository.findByUsername(request.getUserPrincipal().getName());
        userPrincipal.setCreator(false);
        appUserRepository.save(userPrincipal);
        return new RedirectView("/profile" );
    }
//

    @PutMapping("/profile")
    public RedirectView updateProfile(String displayName,
                                      String bio,
                                      String blurb,
                                      Boolean isCreator,
                                      HttpServletRequest request) throws IOException {
        AppUser userPrincipal = appUserRepository.findByUsername(request.getUserPrincipal().getName());
        if (bio != null || bio.isEmpty()) userPrincipal.setBio(bio);
        if (displayName != null || displayName.isEmpty()) userPrincipal.setDisplayName(displayName);
//        if (isCreator != null ) userPrincipal.setCreator(isCreator);
        if (blurb != null || blurb.isEmpty()) userPrincipal.setBlurb(blurb);
        appUserRepository.save(userPrincipal);
        return new RedirectView("profile");
    }

    @PutMapping("/profile/image")
    public RedirectView updateProfile( @RequestParam("image") MultipartFile multipartFile,
                                      HttpServletRequest request) throws IOException {
        AppUser userPrincipal = appUserRepository.findByUsername(request.getUserPrincipal().getName());

        appUserService.updateProfilePicture(userPrincipal, multipartFile);
        appUserRepository.save(userPrincipal);
        return new RedirectView("/profile");
    }
//    to do: make follow redirect to current view
    @PutMapping("/follow/{userId}")
    public RedirectView followUser (HttpServletRequest request, @PathVariable long userId){

        AppUser userPrincipal = appUserRepository.findByUsername(request.getUserPrincipal().getName());
        AppUser userSubject = appUserRepository.findById(userId).get();
        userPrincipal.addFollowing(userSubject);
        userPrincipal.getFollowed().add(userSubject);
        appUserRepository.save(userPrincipal);

        return new RedirectView("/users/" + userId);

    }
    @DeleteMapping("/follow/{id}")
    public RedirectView unfollowUser (HttpServletRequest request, @PathVariable long id){

        AppUser userPrincipal = appUserRepository.findByUsername(request.getUserPrincipal().getName());
        AppUser userSubject = appUserRepository.findById(id).get();
        userPrincipal.removeFollowing(userSubject);
        appUserRepository.save(userSubject);

        return new RedirectView("/users/" + id);

    }

}
