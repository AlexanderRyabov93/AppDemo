package ru.alexapps.personaldictionary

import org.junit.Test

import org.junit.Assert.*
import ru.alexapps.personaldictionary.data.models.BncWord

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_sort() {
        val array = arrayOf(
            BncWord(1, "1", 1),
            BncWord(3, "3", 3 ),
            BncWord(2, "2", 2 ),
            BncWord(4, "4", 4 ),
        )
        val sorted = array.sortWith(compareBy {it.difficulty})
        for(word in array) {
            println(word)
        }

    }
}