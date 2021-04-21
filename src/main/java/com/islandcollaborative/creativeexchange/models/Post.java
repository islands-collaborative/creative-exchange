package com.islandcollaborative.creativeexchange.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String text;
    String title;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    AppUser author;
    @OneToMany(mappedBy = "post")
    List<PostImage> images;
//    @OneToOne
//    PostImage defaultImage;
    @CreationTimestamp
    LocalDateTime createdAt;

    public Post() {
    }

    public Post(String text, String title, AppUser author) {
        this.text = text;
        this.title = title;
        this.author = author;
    }

    public List<PostImage> getImages() {
        return images;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void removeImage(PostImage image) {
        this.images.remove(image);
    }

    public void removeImage(long imageId) {
        this.images.removeIf(i -> i.id == imageId);
    }

    public PostImage getDefaultImage() {
        return images.isEmpty() ? null : images.get(0);
    }

    public boolean getHasDefaultImage() {
        return !images.isEmpty();
    }
}
