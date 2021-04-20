package com.islandcollaborative.creativeexchange.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class AppUserTest {
    AppUser user1 = new AppUser("user1", "password", "user1", false);
    AppUser user2 = new AppUser("user2", "password", "user2", false);
    AppUser user3 = new AppUser("user3", "password", "user3", false);
    AppUser user4 = new AppUser("user4", "password", "user4", false);

    @Test
    public void getThreadsTest() throws InterruptedException {
        user1.getSentMessages().add(new Message(user1, user3, "msg", LocalDateTime.now()));
        user1.getSentMessages().add(new Message(user1, user2, "msg", LocalDateTime.now()));
        user1.getSentMessages().add(new Message(user1, user2, "msg", LocalDateTime.now()));
        Thread.sleep(1); // Java will be too fast and insert al the messages at once if this isn't used.
        Message final2 = new Message(user1, user2, "newest", LocalDateTime.now());
        user1.getSentMessages().add(final2);
        Thread.sleep(1); // Java will be too fast and insert al the messages at once if this isn't used.
        Message final3 = new Message(user3, user1, "newest", LocalDateTime.now());
        user1.getReceivedMessages().add(final3);

        List<Message> result = user1.getThreads();
        Message[] expected = {final3, final2};
        assertArrayEquals(expected, result.toArray(new Message[result.size()]));
    }

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

        List<Message> result = user4.getMessageThread(user1);
        Message[] expected = {msg3, msg2, msg1};
        assertArrayEquals(expected, result.toArray(new Message[result.size()]));
    }
}