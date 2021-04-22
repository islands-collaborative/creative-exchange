package com.islandcollaborative.creativeexchange.repositories;

import com.islandcollaborative.creativeexchange.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.StyledEditorKit;
import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);

    boolean existsByUsername(String username);

    List<AppUser> findByIsCreatorTrue ();
}
