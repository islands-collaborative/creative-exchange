package com.islandcollaborative.creativeexchange.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    AppUser sender;
    @ManyToOne
    @JoinColumn(name = "recipient_user_id")
    AppUser recipient;
    String message;

    @CreationTimestamp
    LocalDateTime createdAt;

    public Message() {
    }

    public Message(AppUser sender, AppUser recipient, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
    }
}
