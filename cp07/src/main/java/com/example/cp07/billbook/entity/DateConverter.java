package com.example.cp07.billbook.entity;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

// 日期类型转换为Long类型
public class DateConverter {

    @TypeConverter
    public static LocalDateTime fromLong(Long milli) {
        if (milli == null) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(milli);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    @TypeConverter
    public static Long dateToLong(LocalDateTime time) {
        return time == null ? null : time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}