package com.islandcollaborative.creativeexchange.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    AppUser author;

    public Comment() {
    }

    public Comment(Post post, AppUser author, String text) {
        this.post = post;
        this.author = author;
        this.text = text;
    }

    String text;

    @CreationTimestamp
    LocalDateTime createdAt;

    LocalDateTime editedAt;

    public long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AppUser getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getEditedAt() {
        return editedAt;
    }

    public void updateEditedAt() {
        editedAt = LocalDateTime.now();
    }
}
