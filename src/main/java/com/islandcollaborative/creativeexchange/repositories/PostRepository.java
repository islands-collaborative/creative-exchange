package com.islandcollaborative.creativeexchange.repositories;

import com.islandcollaborative.creativeexchange.models.AppUser;
import com.islandcollaborative.creativeexchange.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> getByAuthorOrderByCreatedAtDesc(AppUser user);
}
