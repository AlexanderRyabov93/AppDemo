package ru.alexapps.personaldictionary.data.db.typeconverters

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun dateToLong(date: Date): Long {
        return date.time
    }
    @TypeConverter
    fun longToDate(value: Long): Date {
        return Date(value)
    }
 }