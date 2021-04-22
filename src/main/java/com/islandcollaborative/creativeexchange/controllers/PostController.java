package com.islandcollaborative.creativeexchange.controllers;

import com.islandcollaborative.creativeexchange.models.AppUser;
import com.islandcollaborative.creativeexchange.models.Post;
import com.islandcollaborative.creativeexchange.models.PostImage;
import com.islandcollaborative.creativeexchange.repositories.AppUserRepository;
import com.islandcollaborative.creativeexchange.repositories.PostImageRepository;
import com.islandcollaborative.creativeexchange.repositories.PostRepository;
import com.islandcollaborative.creativeexchange.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    PostImageRepository postImageRepository;

    /**
     * GET /posts/
     * Lists all posts
     */
    @GetMapping("/posts")
    public String getAllPosts(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return "posts";
    }

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
        return "post-detail";
    }

    /**
     * @param postId id of the post to get
     * @return redirects to the created posts route.
     * GET /posts/{postId}/edit
     * Requires authentication
     * <p>
     * Gets and returns a view for editing the post
     */
    @GetMapping("/posts/{postId}/edit")
    public String getPostEditView(@PathVariable long postId,
                                  Principal principal,
                                  Model model) {
        AppUser userPrincipal = appUserRepository.findByUsername(principal.getName());
        model.addAttribute("post", postRepository.getOne(postId));
        return "post-edit";
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
    public RedirectView addPost(HttpServletRequest request, String text, String title, String author) {
        AppUser userPrincipal = appUserRepository.findByUsername(request.getUserPrincipal().getName());
        Post post = new Post(text, title, userPrincipal);
        postRepository.save(post);
        return new RedirectView("posts");
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
    public RedirectView updatePosts(@PathVariable long postId, String text, String title) {
        Post post = postRepository.getOne(postId);
        post.setText(text);
        post.setTitle(title);
        postRepository.save(post);
        return new RedirectView("/posts/" + postId + "/edit");
    }

    /**
     * @return redirects to the modified posts route.
     * POST /posts/{postId}/images
     * Requires authentication
     * <p>
     * Adds an image to a post. Redirects to the post's edit page.
     */
    @PostMapping("/posts/{postId}/images")
    public RedirectView addImage(@PathVariable long postId,
                                 @RequestParam("images") MultipartFile[] images) {
        Post post = postRepository.getOne(postId);
        try {
            postService.addImages(post, images);
        } catch (Exception e) {

        }
        postRepository.save(post);
        return new RedirectView("/posts/" + postId + "/edit");
    }

    /**
     * @return redirects to the modified posts route.
     * PUT /posts/{postId}/images/{imageId}
     * Requires authentication
     * <p>
     * Modifies properties of an image on a post. Redirects to post's edit page.
     */
    @PutMapping("/posts/{postId}/images/{imageId}")
    public RedirectView editImage(@PathVariable long imageId,
                                  @PathVariable long postId,
                                  String description) {
        PostImage image = postImageRepository.getOne(imageId);

        if (description != null) image.setDescription(description);
        postImageRepository.save(image);
        return new RedirectView("/posts/" + postId + "/edit");
    }

    /**
     * @return redirects to the modified posts route.
     * DELETE /posts/{postId}/images/{imageId}
     * Requires authentication
     * <p>
     * Deletes an image from a post. Redirects to the post's edit page.
     */
    @DeleteMapping("/posts/{postId}/images/{imageId}")
    public RedirectView deleteImage(@PathVariable long imageId,
                                    @PathVariable long postId) {
        PostImage image = postImageRepository.getOne(imageId);
        Post post = postRepository.getOne(postId);
        postImageRepository.delete(image);
        postService.removeImage(image);
        return new RedirectView("/posts/" + postId + "/edit");
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

    /**
     * @return The template name of the feed route
     * GET /feed
     * Requires authentication
     * <p>
     * Displays a user's feed with no pagination.
     */
    @GetMapping("/feed")
    public String getFeed(Model m, Principal principal) {
        AppUser userPrincipal = appUserRepository.findByUsername(principal.getName());
        List<Post> feed = postService.getUserFeed(userPrincipal);
        m.addAttribute("feed", feed);
        return "feed";
    }

}