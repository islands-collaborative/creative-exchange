package com.islandcollaborative.creativeexchange.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String extension;
    String description;
    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;
    @CreationTimestamp
    LocalDateTime createdAt;

    public PostImage() {
    }

    public PostImage(String extension, Post post, String description) {
        this.extension = extension;
        this.description = description;
        this.post = post;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public long getId() {
        return id;
    }

    public String getExtension() {
        return extension;
    }

    public String getFilename() {
        return "" + id + extension;
    }

    public void setExtension(String filename) {
        this.extension = filename;
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
