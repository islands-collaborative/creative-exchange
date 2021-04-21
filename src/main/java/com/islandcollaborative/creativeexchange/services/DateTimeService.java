package com.islandcollaborative.creativeexchange.services;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class DateTimeService {
    public String formattedDateTime(LocalDateTime date) {
        if (date.isAfter(LocalDateTime.now().minus(5, ChronoUnit.SECONDS))) return "now";
//        if (date.isAfter(date.minus(1, ChronoUnit.HOURS))) {}
        if (date.isAfter(LocalDateTime.now().minus(24, ChronoUnit.HOURS))){
            return date.format(DateTimeFormatter.ofPattern("h:mm a"));
        }
        return "sometime";
    }
}
