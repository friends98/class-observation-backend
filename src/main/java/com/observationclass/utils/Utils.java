package com.observationclass.utils;

import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);
    public static Timestamp resultTimeStamp(){
        LocalDateTime localDateTimeWithoutTimeZone = Instant.now().atOffset(ZoneOffset.UTC).toLocalDateTime();
        return Timestamp.valueOf(localDateTimeWithoutTimeZone);
    }
    public static Timestamp resultTimestamp() {
       
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        LocalDateTime localDateTimeWithoutTimeZone = LocalDateTime.now();
        logger.info("Đây là thời gian thực :{}",localDateTimeWithoutTimeZone);
        return Timestamp.valueOf(dateTimeFormatter.format(localDateTimeWithoutTimeZone));
    }
    public static <T> void addScalr(NativeQuery<?> sqlQuery, Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException("[clazz] could not be null!");
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == long.class || field.getType() == Long.class) {
                sqlQuery.addScalar(field.getName(), StandardBasicTypes.LONG);
            }
            if (field.getType() == int.class || field.getType() == Integer.class) {
                sqlQuery.addScalar(field.getName(), StandardBasicTypes.INTEGER);
            }
            if (field.getType() == char.class || field.getType() == Character.class) {
                sqlQuery.addScalar(field.getName(), StandardBasicTypes.CHARACTER);
            }
            if (field.getType() == short.class || field.getType() == Short.class) {
                sqlQuery.addScalar(field.getName(), StandardBasicTypes.SHORT);
            }
            if (field.getType() == double.class || field.getType() == Double.class) {
                sqlQuery.addScalar(field.getName(), StandardBasicTypes.DOUBLE);
            }
            if (field.getType() == float.class || field.getType() == Float.class) {
                sqlQuery.addScalar(field.getName(), StandardBasicTypes.FLOAT);
            }
            if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                sqlQuery.addScalar(field.getName(), StandardBasicTypes.BOOLEAN);
            }
            if (field.getType() == String.class) {
                sqlQuery.addScalar(field.getName(), StandardBasicTypes.STRING);
            }
            if (field.getType() == Date.class) {
                sqlQuery.addScalar(field.getName(), StandardBasicTypes.DATE);
            }
            if (field.getType() == Timestamp.class) {
                sqlQuery.addScalar(field.getName(), StandardBasicTypes.TIMESTAMP);
            }
        }
        sqlQuery.setResultTransformer(Transformers.aliasToBean(clazz));
    }

}
