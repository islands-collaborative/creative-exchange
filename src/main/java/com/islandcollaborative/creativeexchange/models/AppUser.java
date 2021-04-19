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
}
