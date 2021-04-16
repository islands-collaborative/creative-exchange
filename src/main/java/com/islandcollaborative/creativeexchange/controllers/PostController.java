package com.islandcollaborative.creativeexchange.controllers;

import com.islandcollaborative.creativeexchange.repositories.AppUserRepository;
import com.islandcollaborative.creativeexchange.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    AppUserRepository appUserRepository;

    /** Requires authentication. Creates a new post. Redirects to the user's private-facing profile page */
    @PostMapping("/posts")
    public RedirectView addPost(){
        return new RedirectView("");
    }

    @PutMapping("/posts/{postId}")
    public RedirectView updatePosts(){
        return new RedirectView("");
    }

    @DeleteMapping("/posts/{postId}")
    public RedirectView deletePosts(){
        return new RedirectView("");
    }

}


//POST /posts: Requires authentication. Creates a new post. Redirects to the user's private-facing profile page at /profile. Alternative redirect: /posts/{postId}. Alternatively redirect to their public-facing profile page at /users/{userId}.
//        PUT /posts/{postId}: Requires authentication. Edits a post. Same redirect as above.
//        DELETE /posts/{postId}: Requires authentication. Marks a post for deletion. Same redirect as above.
