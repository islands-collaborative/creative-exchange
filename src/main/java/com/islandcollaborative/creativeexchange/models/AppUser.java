package com.islandcollaborative.creativeexchange.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(unique = true)
    String username;
    String password;
    String displayName;
    String blurb;
    Boolean isCreator;
    //TODO Picture

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Message> sentMessages;
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Message> receivedMessages;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Post> posts;

    //TODO follow another user

    @CreationTimestamp
    LocalDateTime createdAt;
}
