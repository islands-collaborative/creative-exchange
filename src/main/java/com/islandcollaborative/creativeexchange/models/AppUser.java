package com.islandcollaborative.creativeexchange.models;

import com.islandcollaborative.creativeexchange.services.MessageService;
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
    Boolean isCreator = false;
    String imageFilename;

    //TODO Picture

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Message> sentMessages = new ArrayList<>();
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Message> receivedMessages = new ArrayList<>();
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Post> posts = new ArrayList<>();



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
    public AppUser(String username, String password, String displayName) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;

    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
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
     * <p>
     * Returns a list of the most recent message from each thread for this user.
     */
    public List<Message> getThreads() {
        return MessageService.getThreads(this);
    }

    /**
     * @param user user with which to find related messages.
     * @return List<Message>
     * <p>
     * Gets a list of messages to represent the thread with a single other user and sorts them by
     * date sent. (newest is at index 0)
     */
    public List<Message> getMessageThread(AppUser user) {
        return MessageService.getMessageThread(user, this);
    }
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "following_relations",
            joinColumns = {@JoinColumn(name = "follow_from")},
            inverseJoinColumns = {@JoinColumn(name = "follow_to")}
    )
//     the people you follow
    Set<AppUser> followed = new HashSet<>();
    @ManyToMany(mappedBy = "followed")
    Set<AppUser> followers = new HashSet<>();
//    the people following you
    public Set<AppUser> getFollowers() {
        return followers;
    }


    public Set<AppUser> getFollowed() {
        return followed;
    }

    public void addFollowing(AppUser userToFollow){
        followed.add(userToFollow);
    }
    public void removeFollowing(AppUser userToRemove){
        followed.remove(userToRemove);
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public long getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName == null ? username : displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Boolean getCreator() {
        return isCreator;
    }
    //    the standard getters and setters weren't getting along with thymeleaf
    public Boolean creatorCheck (){
        if (isCreator == true) return true;
        else return false;
    }

    public void setCreator(Boolean creator) {
        isCreator = creator;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    public void setImageFilename(String filename) {
        imageFilename = filename;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", displayName='" + displayName + '\'' +
                ", bio='" + bio + '\'' +
                ", isCreator=" + isCreator +
                ", createdAt=" + createdAt +
                '}';
    }

    public Boolean hasProfilePicture (){
        if (imageFilename == null) return false;
        else return true;
    }


}
