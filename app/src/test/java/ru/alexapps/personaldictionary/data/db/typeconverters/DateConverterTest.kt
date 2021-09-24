package ru.alexapps.personaldictionary.data.db.typeconverters

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class DateConverterTest {
    private val dateConverter = DateConverter()

    @Test
    fun `convert date to long`() {
        val currentTime = System.currentTimeMillis()
        val date = Date(currentTime)
        assertEquals(currentTime, dateConverter.dateToLong(date))
    }
    @Test
    fun `convert long to date`() {
        val currentTime = System.currentTimeMillis()
        assertEquals(Date(currentTime), dateConverter.longToDate(currentTime))
    }

}