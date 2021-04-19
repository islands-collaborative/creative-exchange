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
    String title;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    AppUser author;

    String imagePath;
    /* @Column(columnDefinition = "")
    List<String> imagePaths; */

    @CreationTimestamp
    LocalDateTime createdAt;

    public Post(){}

    public Post(String post, String title, AppUser author, String imagePath) {
        this.post = post;
        this.title = title;
        this.author = author;
        this.imagePath = imagePath;
    }

    public long getId() {
        return id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AppUser getAuthor() {
        return author;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
