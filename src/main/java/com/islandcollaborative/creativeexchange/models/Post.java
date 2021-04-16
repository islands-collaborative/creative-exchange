package com.islandcollaborative.creativeexchange.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(columnDefinition = "TEXT")
    String post;
    @ManyToOne
    @JoinColumn(name = "app_user_id")
    //TODO Picture

    @CreationTimestamp
    LocalDateTime createdAt;


    public Post(){}


}
