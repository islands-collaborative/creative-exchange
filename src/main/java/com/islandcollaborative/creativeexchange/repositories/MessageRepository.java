package com.islandcollaborative.creativeexchange.repositories;

import com.islandcollaborative.creativeexchange.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
