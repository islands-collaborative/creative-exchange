package com.islandcollaborative.creativeexchange.repositories;


import com.islandcollaborative.creativeexchange.models.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
