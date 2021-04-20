package com.islandcollaborative.creativeexchange.services;

import com.islandcollaborative.creativeexchange.models.AppUser;
import com.islandcollaborative.creativeexchange.models.Message;
import com.islandcollaborative.creativeexchange.repositories.AppUserRepository;
import com.islandcollaborative.creativeexchange.repositories.MessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


public class MessageService {
    @Autowired
    MessageRepository messageRepo;

    @Autowired
    AppUserRepository userRepo;

    AppUser user1 = new AppUser("user1", "password", "user1", false);
    AppUser user2 = new AppUser("user2", "password", "user2", false);
    AppUser user3 = new AppUser("user3", "password", "user3", false);
    AppUser user4 = new AppUser("user4", "password", "user4", false);

    @Test
    public void getMessageThreadTest() throws InterruptedException {
        Message msg1 = new Message(user4, user1, "m1", LocalDateTime.now());
        user4.getSentMessages().add(msg1);
        Thread.sleep(1); // Java will be too fast and insert al the messages at once if this isn't used.
        Message msg2 = new Message(user4, user1, "m2", LocalDateTime.now());
        user4.getSentMessages().add(msg2);
        Thread.sleep(1); // Java will be too fast and insert al the messages at once if this isn't used.
        Message msg3 = new Message(user1, user4, "m3", LocalDateTime.now());
        user4.getReceivedMessages().add(msg3);

        userRepo.save(user4);

        List<Message> result = messageRepo.getAllMessages(user1.getId(), user2.getId());
        Message[] expected = {msg3, msg2, msg1};
        assertArrayEquals(expected, result.toArray(new Message[result.size()]));
    }
}
