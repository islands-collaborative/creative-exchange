package com.islandcollaborative.creativeexchange.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String filename;
    String description;
    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;
    @CreationTimestamp
    LocalDateTime createdAt;

    public PostImage() {
    }

    public PostImage(String filename, Post post, String description) {
        this.filename = filename;
        this.description = description;
        this.post = post;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public long getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Post getPost() {
        return post;
    }
}
