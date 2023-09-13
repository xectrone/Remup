package com.example.remup.data.database

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class AppConverters
{

        @TypeConverter
        fun fromTimestamp(value: Long?) = value?.let { LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneOffset.UTC) }

        @TypeConverter
        fun dateToTimestamp(date: LocalDateTime?) = date?.atZone(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()

}