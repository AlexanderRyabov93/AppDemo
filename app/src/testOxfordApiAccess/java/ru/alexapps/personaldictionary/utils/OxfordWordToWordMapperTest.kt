package ru.alexapps.personaldictionary.utils

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.alexapps.personaldictionary.data.models.Sense
import ru.alexapps.personaldictionary.data.models.Word
import ru.alexapps.personaldictionary.data.network.retrofitmodels.OxfordWord

class OxfordWordToWordMapperTest {

    private lateinit var mapper: OxfordWordToWordMapper
    private val oxfordWord: OxfordWord =
        ru.alexapps.personaldictionary.testHelpers.OxfordWordData.oxfordWord
    private val words: Array<Word> = ru.alexapps.personaldictionary.testHelpers.OxfordWordData.words

    @Before
    fun setup() {
        mapper = spy(OxfordWordToWordMapper())
    }

    @Test
    fun `oxfordSenseRoSense should return correct value`() {
        val sense = oxfordWord.results[0].lexicalEntries[0].entries[0].senses[0]
        val result = mapper.oxfordSenseToSense(sense)
        Assert.assertEquals(
            Sense(
                0,
                sense.definitions.toTypedArray(),
                sense.examples.map { it.text }.toTypedArray()
            ),
            result
        )
    }

    @Test
    fun `oxfordEntriesToSenses should return correct value`() {
        val oxfordEntries = oxfordWord.results[0].lexicalEntries[0].entries
        val oxfordSense1 = oxfordEntries[0].senses[0]
        val oxfordSense2 = oxfordEntries[0].senses[1]
        val oxfordSense3 = oxfordEntries[1].senses[0]
        val result = mapper.oxfordEntriesToSenses(oxfordEntries)
        Assert.assertArrayEquals(
            arrayOf(
                Sense(
                    0,
                    oxfordSense1.definitions.toTypedArray(),
                    oxfordSense1.examples.map { it.text }.toTypedArray()
                ),
                Sense(
                    0,
                    oxfordSense2.definitions.toTypedArray(),
                    oxfordSense2.examples.map { it.text }.toTypedArray()
                ),
                Sense(
                    0,
                    oxfordSense3.definitions.toTypedArray(),
                    oxfordSense3.examples.map { it.text }.toTypedArray()
                )
            ),
            result
        )
        verify(mapper, times(3)).oxfordSenseToSense(any())
    }

    @Test
    fun `lexicalEntryToWord should return correct value`() {
        val lexicalEntry = oxfordWord.results[0].lexicalEntries[0]
        val result = mapper.lexicalEntryToWord(lexicalEntry)
        Assert.assertEquals(
            words[0],
            result
        )
        verify(mapper, times(1)).oxfordEntriesToSenses(any())
    }

    @Test
    fun `oxfordWordResultToWords should return correct value`() {
        val oxfordWordResult = oxfordWord.results[0]
        val result = mapper.oxfordWordResultToWords(oxfordWordResult)
        Assert.assertArrayEquals(
            arrayOf(
                words[0]
            ),
            result.toTypedArray()
        )
        verify(mapper, times(1)).lexicalEntryToWord(any())
    }

    @Test
    fun `oxfordWordToWords should return correct value`() {
        val result = mapper.map(oxfordWord)
        Assert.assertArrayEquals(
            words,
            result.toTypedArray()
        )
        verify(mapper, times(2)).oxfordWordResultToWords(any())
    }

}