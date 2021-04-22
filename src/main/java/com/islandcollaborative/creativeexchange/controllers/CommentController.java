package com.islandcollaborative.creativeexchange.controllers;

import com.islandcollaborative.creativeexchange.models.AppUser;
import com.islandcollaborative.creativeexchange.models.Comment;
import com.islandcollaborative.creativeexchange.models.Post;
import com.islandcollaborative.creativeexchange.repositories.AppUserRepository;
import com.islandcollaborative.creativeexchange.repositories.CommentRepository;
import com.islandcollaborative.creativeexchange.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class CommentController {
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;

    /**
     * POST /posts/{postId}/comments
     * @param postId
     * @param text
     * @param principal
     * @return By default redirects the user to the post detail page at /posts/{postId}.
     * Requires auth. Makes a comment on a post.
     */
    @PostMapping("/posts/{postId}/comments")
    public RedirectView addComment(@PathVariable long postId,
                                   String text,
                                   Principal principal) {
        AppUser userPrincipal = appUserRepository.findByUsername(principal.getName());
        Post post = postRepository.getOne(postId);

        Comment comment = new Comment(post, userPrincipal, text);
        commentRepository.save(comment);

        return new RedirectView("/posts/" + postId);
    }

    /**
     * POST /posts/{postId}/comments/{commentId}
     * @param postId
     * @param commentId
     * @param text
     * @param principal
     * @return By default redirects the user to the post detail page at /posts/{postId}.
     * Requires auth. Edits a comment on a post.
     */
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public RedirectView addComment(@PathVariable long postId,
                                   @PathVariable long commentId,
                                   String text,
                                   Principal principal) {
        AppUser userPrincipal = appUserRepository.findByUsername(principal.getName());
        Comment comment = commentRepository.getOne(commentId);

        if (userPrincipal.getId() != comment.getAuthor().getId()) {
            return new RedirectView("/posts/" + postId + "?error=invalid");
        }

        if (text != null) {
            comment.setText(text);
            comment.updateEditedAt();
            commentRepository.save(comment);
        }

        return new RedirectView("/posts/" + postId);
    }

//    DELETE /posts/{postId}/comments/{commentId}: Requires auth. Deletes a comment on a website. Takes a redirect parameter that redirects the user to the same view that there were at when they posted the comment. By default redirects the user to the post detail page at /posts/{postId}.
    /**
     * DELETE /posts/{postId}/comments/{commentId}
     * @param postId
     * @param commentId
     * @param principal
     * @return By default redirects the user to the post detail page at /posts/{postId}.
     * Requires auth. Deletes a comment on a post.
     */
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public RedirectView deleteComment(@PathVariable long postId,
                                   @PathVariable long commentId,
                                   Principal principal) {
        AppUser userPrincipal = appUserRepository.findByUsername(principal.getName());
        Comment comment = commentRepository.getOne(commentId);

        if (userPrincipal.getId() != comment.getAuthor().getId()) {
            return new RedirectView("/posts/" + postId + "?error=invalid");
        }

        commentRepository.delete(comment);

        return new RedirectView("/posts/" + postId);
    }
}
