package com.islandcollaborative.creativeexchange.services;

import com.islandcollaborative.creativeexchange.models.AppUser;
import com.islandcollaborative.creativeexchange.models.Message;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MessageService {
    static Comparator<Message> compareByCreatedTime = (Message v1, Message v2) -> v2.getCreatedAt().compareTo(v1.getCreatedAt());

    /**
     * @return List<Message>
     * <p>
     * Returns a list of the most recent message from each thread for this user.
     */
    public static List<Message> getThreads(List<Message> sentMessages, List<Message> receivedMessages) {
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

        Collections.sort(result, compareByCreatedTime);

        return result;
    }

    /**
     * @param user user to filter messages on.
     * @return List<Message>
     * <p>
     * filters messages and sorts them by date sent. (newest is at index 0)
     */
    public static List<Message> getMessageThread(AppUser user, List<Message> sentMessages, List<Message> receivedMessages) {
        return Stream.concat(
                sentMessages.stream().filter(p -> p.getRecipient() == user),
                receivedMessages.stream().filter(p -> p.getSender() == user)
        ).sorted(compareByCreatedTime).collect(Collectors.toList());
    }
}
