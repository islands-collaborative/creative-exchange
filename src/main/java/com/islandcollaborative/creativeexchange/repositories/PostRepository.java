package com.islandcollaborative.creativeexchange.repositories;

import com.islandcollaborative.creativeexchange.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
