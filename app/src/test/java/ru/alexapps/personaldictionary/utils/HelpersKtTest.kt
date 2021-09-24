package ru.alexapps.personaldictionary.utils

import org.junit.Assert.*
import org.junit.Test

class HelpersKtTest {

    @Test
    fun `stringArrayToString should return correct string`() {
        val array = arrayOf("string1", "string2", "String3")
        assertEquals("String1\nString2\nString3", stringArrayToString(array))
    }
    @Test
    fun `stringArrayToString should return empty string`() {
        val array: Array<String> = emptyArray()
        assertEquals("", stringArrayToString(array))
    }
}