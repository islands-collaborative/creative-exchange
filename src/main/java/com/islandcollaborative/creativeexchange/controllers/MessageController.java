package com.islandcollaborative.creativeexchange.controllers;

import com.islandcollaborative.creativeexchange.models.AppUser;
import com.islandcollaborative.creativeexchange.models.Message;
import com.islandcollaborative.creativeexchange.repositories.AppUserRepository;
import com.islandcollaborative.creativeexchange.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    MessageRepository messageRepository;

    /**
     * @return htmlPage
     * GET "/messages"
     * Requires authentication.
     * <p>
     * "Inbox view". Displays a list of message threads that the currently logged in user
     * is a part of. Sorts the threads by how recent a message was posted and whether there
     * are unread messages.
     */
    @GetMapping("/messages")
    public String getInbox(HttpServletRequest request, Model m) {
        AppUser userPrincipal = appUserRepository.findByUsername(request.getUserPrincipal().getName());
        m.addAttribute("messages", userPrincipal.getThreads());
        return "inbox";
    }

    /**
     * @param userId id of user who's conversation to show.
     * @return htmlPage
     * GET "/messages"
     * Requires authentication.
     * <p>
     * Displays a thread of the messages between the currently logged in user and the subject
     * user indicated by the `{userId}` parameter. Also provides a form for the user to send a
     * message in that thread.
     */
    @GetMapping("/users/{userId}/messages")
    public String getThread(@PathVariable long userId, HttpServletRequest request, Model m) {
//        AppUser userPrincipal = appUserRepository.findByUsername(request.getUserPrincipal().getName());
//        m.addAttribute("messages", userPrincipal.getMessageThread(appUserRepository.getOne(userId)));



        AppUser userPrincipal = appUserRepository.findByUsername(request.getUserPrincipal().getName());
        AppUser userSubject = appUserRepository.getOne(userId);

        List<Message> thread = messageRepository.getAllMessages(userPrincipal.getId(), userSubject.getId());
        m.addAttribute("messages", thread);
        return "thread";
    }

    /**
     * @param userId AppUser to send the message to.
     * @param text content of the message to be sent.
     * @return Redirects to "/users/{userId}/messages"
     * Post /messages
     * Requires authentication
     *
     * Sends a message from the currently logged in user to the subject user. Redirects to the
     * message thread at `/users/{userId}/messages`.
     */
    @PostMapping("/messages")
    public RedirectView sendMessage(Long userId,
                                    HttpServletRequest request,
                                    Model m,
                                    String text) {
        AppUser userPrincipal = appUserRepository.findByUsername(request.getUserPrincipal().getName());
        AppUser recipient = appUserRepository.getOne(userId);
        Message message = new Message(userPrincipal, recipient, text);
        messageRepository.save(message);
        return new RedirectView("/users/" + userId + "/messages");
    }

    // - `PUT /users/{userId}/messages/{messageId}`: (Stretch goal). Requires authentication. Edits a message. Redirects to the message thread at `/users/{userId}/messages`.
    @PutMapping("/users/{userId}/messages/{messageId}")
    public RedirectView editMessage(@PathVariable long userId,
                                    @PathVariable long messageId) {
        return new RedirectView("/users/" + userId + "/messages");
    }

    // - `DELETE /users/{userId}/messages/{messageId}`: (Stretch goal). Requires authentication. Deletes a message. Redirects to the message thread at `/users/{userId}/messages`.
    @DeleteMapping("/users/{userId}/messages/{messageId}")
    public RedirectView deleteMessage(@PathVariable long userId,
                                      @PathVariable long messageId) {
        return new RedirectView("/users/" + userId + "/messages");
    }
}