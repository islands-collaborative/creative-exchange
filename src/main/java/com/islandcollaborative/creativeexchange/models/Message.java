package com.islandcollaborative.creativeexchange.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @ManyToOne
    @JoinColumn(name = "from_app_user_id")
    AppUser fromUser;
    @ManyToOne
    @JoinColumn(name = "to_app_user_id")
    AppUser toUser;
    String message;

    @CreationTimestamp
    LocalDateTime createdAt;
}
