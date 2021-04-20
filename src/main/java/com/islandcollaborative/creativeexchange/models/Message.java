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


    /**
     * @param createdAt to only be sued for testing.
     *
     * No risk of using this in prod as the date will be rewritten upon save and become immutable
     * after that.
     */
    public Message(AppUser sender, AppUser recipient, String message, LocalDateTime createdAt) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.createdAt = createdAt;
    }

    public AppUser getSender() {
        return sender;
    }

    public AppUser getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sender=" + sender.displayName +
                ", recipient=" + recipient.displayName +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
