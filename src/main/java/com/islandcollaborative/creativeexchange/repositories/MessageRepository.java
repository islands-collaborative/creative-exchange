package com.islandcollaborative.creativeexchange.repositories;

import com.islandcollaborative.creativeexchange.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
        @Query("select m from Message m where " +
                "(m.sender = 1 and m.recipient = 2) or " +
                "(m.sender = 2 and m.recipient = 1) " +
                "order by m.createdAt")
    public List<Message> getAllMessages(long user1, long user2);
}