package ru.alexapps.personaldictionary.utils

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Test
import ru.alexapps.personaldictionary.data.models.Sense
import ru.alexapps.personaldictionary.data.network.retrofitmodels.wordsapi.RapidApiWordResult
import ru.alexapps.personaldictionary.testHelpers.RapidApiWordsData

class RapidApiWordToWordsTest {
    private lateinit var mapper: RapidApiWordToWords
    private val rapidApiWord = RapidApiWordsData.rapidApiWord
    private val words = RapidApiWordsData.words

    @Before
    fun setup() {
        mapper = spy(RapidApiWordToWords())
    }

    @Test
    fun `getSenses should return correct value null examples`() {
        val wordResult = RapidApiWordResult("definition1", "noun", null)
        assertArrayEquals(
            arrayOf(Sense(0, arrayOf(wordResult.definition), emptyArray())),

            mapper.getSenses(wordResult)
        )
    }

    @Test
    fun `getSenses should return correct value empty examples`() {
        val wordResult = RapidApiWordResult("definition1", "noun", emptyList())
        assertArrayEquals(
            arrayOf(Sense(0, arrayOf(wordResult.definition), emptyArray())),
            mapper.getSenses(wordResult)
        )
    }

    @Test
    fun `getSenses should return correct value not empty examples`() {
        val wordResult = RapidApiWordResult("definition1", "noun", listOf("example1, example2"))
        assertArrayEquals(
            arrayOf(
                Sense(
                    0,
                    arrayOf(wordResult.definition),
                    arrayOf("example1, example2")
                )
            ), mapper.getSenses(wordResult)
        )
    }

    @Test
    fun `rapidApiWordToWords should return correct value`() {
        val result = mapper.map(rapidApiWord)
        verify(mapper, times(rapidApiWord.results.size)).getSenses(any())
        assertArrayEquals(words, result.toTypedArray())
    }
}