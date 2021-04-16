package com.islandcollaborative.creativeexchange.repositories;

import com.islandcollaborative.creativeexchange.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
