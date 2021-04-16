package com.islandcollaborative.creativeexchange.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class MessageController {

    // - `GET /messages`: Requires authentication. "Inbox view". Displays a list of message threads that the currently logged in user is a part of. Sorts the threads by how recent a message was posted and whether there are unread messages.
    @GetMapping("/messages")
    public String getInbox() {
        return "inbox";
    }

    // - `GET /users/{userId}/messages`: Requires authentication. Displays a thread of the messages between the currently logged in user and the subject user indicated by the `{userId}` parameter. Also provides a form for the user to send a message in that thread.
    @GetMapping("/users/{userId}/messages")
    public String getThread(@PathVariable long userId) {
        return "thread";
    }

    // - `POST /users/{userId}/messages`: Requires authentication. Sends a message from the currently logged in user to the subject user. Redirects to the message thread at `/users/{userId}/messages`.
    @PostMapping("/users/{userId}/messages")
    public RedirectView sendMessage(@PathVariable("userId") long userId) {
        return new RedirectView("/users/" + userId + "/messages");
    }

    // - `PUT /users/{userId}/messages/{messageId}`: (Stretch goal). Requires authentication. Edits a message. Redirects to the message thread at `/users/{userId}/messages`.
    @PutMapping("/users/{userId}/messages/{messageId}")
    public RedirectView editMessage(@PathVariable long userId,
                                    @PathVariable long messageId) {
        return new RedirectView("/users/" + userId + "/messages");
    }

    // - `DELETE /users/{userId}/messages/{messageId}`: (Stretch goal). Requires authentication. Deletes a message. Redirects to the message thread at `/users/{userId}/messages`.
    @PutMapping("/users/{userId}/messages/{messageId}")
    public RedirectView deleteMessage(@PathVariable long userId,
                                      @PathVariable long messageId) {
        return new RedirectView("/users/" + userId + "/messages");
    }
}