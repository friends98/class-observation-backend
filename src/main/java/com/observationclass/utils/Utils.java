package com.observationclass.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);
    public static Timestamp resultTimeStamp(){
        LocalDateTime localDateTimeWithoutTimeZone = Instant.now().atOffset(ZoneOffset.UTC).toLocalDateTime();
        return Timestamp.valueOf(localDateTimeWithoutTimeZone);
    }
    public static Timestamp resultTimestamp() {
        // HoanNNC update common remove time zone
        LocalDateTime localDateTimeWithoutTimeZone = Instant.now().atOffset(ZoneOffset.UTC).toLocalDateTime();
        return Timestamp.valueOf(localDateTimeWithoutTimeZone);
    }
}
