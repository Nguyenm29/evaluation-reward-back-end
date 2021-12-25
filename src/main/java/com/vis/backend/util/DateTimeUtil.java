package com.vis.backend.util;

import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;

@Component
public class DateTimeUtil {

    public final  String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public Timestamp getCurrentTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }

}
