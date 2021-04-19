package com.islandcollaborative.creativeexchange.controllers;

import com.islandcollaborative.creativeexchange.models.AppUser;
import com.islandcollaborative.creativeexchange.models.Post;
import com.islandcollaborative.creativeexchange.repositories.AppUserRepository;
import com.islandcollaborative.creativeexchange.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    AppUserRepository appUserRepository;

    /**
     * @param postId id of the post to get
     * @return redirects to the created posts route.
     * GET /posts/{postId}
     * Requires authentication
     * <p>
     * gets and returns the specified post.
     */
    @GetMapping("/posts/{postId}")
    public String getPost(@PathVariable long postId, Model m) {
        m.addAttribute("post", postRepository.getOne(postId));
        return "post";
    }

    /**
     * @return redirects to the created posts route.
     * POST /posts
     * Requires authentication
     * <p>
     * Creates a new post. Redirects to the user's private-facing profile page at /profile.
     * Alternative redirect: /posts/{postId}. Alternatively redirect to their public-facing
     * profile page at /users/{userId}.
     */
    @PostMapping("/posts")
    public RedirectView addPost(HttpServletRequest request, String postText, String title, String author, String imagePath) {
        AppUser userPrincipal = appUserRepository.findByUsername(request.getUserPrincipal().getName());
        Post post = new Post(postText, title, userPrincipal, imagePath);
        postRepository.save(post);
        return new RedirectView("");
    }

    /**
     * @return redirects to the modified posts route.
     * PUT /posts/{postId}
     * Requires authentication
     * <p>
     * Edits a post. Redirects to the user's private-facing profile page at /profile.
     * Alternative redirect: /posts/{postId}. Alternatively redirect to their public-facing
     * profile page at /users/{userId}.
     */
    @PutMapping("/posts/{postId}")
    public RedirectView updatePosts(@PathVariable long postId, String postText, String title, String imagePath) {
        Post post = postRepository.getOne(postId);
        post.setPost(postText);
        post.setTitle(title);
        post.setImagePath(imagePath);
        postRepository.save(post);
        return new RedirectView("");
    }

    /**
     * @return redirects to the modified posts route.
     * PUT /posts/{postId}
     * Requires authentication
     * <p>
     * Deletes a post. Redirects to the user's private-facing profile page at /profile.
     */
    @DeleteMapping("/posts/{postId}")
    public RedirectView deletePosts(@PathVariable long postId) {
        postRepository.deleteById(postId);
        return new RedirectView("/profile");
    }

}