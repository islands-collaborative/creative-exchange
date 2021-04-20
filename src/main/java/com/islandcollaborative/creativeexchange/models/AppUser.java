package com.islandcollaborative.creativeexchange.models;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    //TODO Picture

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Message> sentMessages = new ArrayList<>();
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Message> receivedMessages = new ArrayList<>();
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Post> posts = new ArrayList<>();

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
     * <p>
     * Returns a list of the most recent message from each thread for this user.
     */
    public List<Message> getThreads() {
        //group by the user messages are with and retrieve the most recent message.
        Map<AppUser, Message> mostRecentSent = sentMessages.stream()
                .collect(Collectors.toMap(Message::getRecipient, Function.identity(),
                        BinaryOperator.maxBy(Comparator.comparing(Message::getCreatedAt))));

        Map<AppUser, Message> mostRecentReceived = receivedMessages.stream()
                .collect(Collectors.toMap(Message::getSender, Function.identity(),
                        BinaryOperator.maxBy(Comparator.comparing(Message::getCreatedAt))));

        //group the too maps and sort into list.
        List<Message> result = new ArrayList<>(Stream.of(mostRecentSent, mostRecentReceived)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> {
                    if (v1.getCreatedAt().compareTo(v2.getCreatedAt()) > 0) return v1;
                    return v2;
                })).values());

        Comparator<Message> compareByCreatedTime = (Message v1, Message v2) -> v2.getCreatedAt().compareTo(v1.getCreatedAt());
        Collections.sort(result, compareByCreatedTime);

        return result;
    }

    /**
     * @param ThreadWithId Id of related user.
     * @return List<Message>
     * <p>
     * Gets a list of messages to represent the thread with a single other user.
     */
    public List<Message> getMessageThread(Long ThreadWithId) {
        //todo
        return sentMessages;
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
        return displayName;
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

    public void setCreator(Boolean creator) {
        isCreator = creator;
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
}
