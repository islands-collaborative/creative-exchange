package com.islandcollaborative.creativeexchange.models;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(unique = true)
    String username;
    String password;
    String displayName;
    @Column(columnDefinition = "TEXT")
    String bio;
    String blurb;
    Boolean isCreator;
    String imageFilename;
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

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public AppUser() {
    }

    public AppUser(String username, String password, String displayName, Boolean isCreator) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.isCreator = isCreator;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * @return List<Message>
     *
     * Returns a list of the most recent message from each thread for this user.
     */
    public List<Message> getThreads() {
        //todo
        return sentMessages;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    /**
     * @param ThreadWithId Id of related user.
     * @return List<Message>
     *
     * Gets a list of messages to represent the thread with a single other user.
     */
    public List<Message> getMessageThread(Long ThreadWithId) {
        //todo
        return sentMessages;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setCreator(Boolean creator) {
        isCreator = creator;
    }


    public long getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getBio() {
        return bio;
    }

    public Boolean getCreator() {
        return isCreator;
    }

    public String getImageFilename() { return imageFilename; }

    public void setImageFilename(String filename) {
        imageFilename = filename;
    }
}
