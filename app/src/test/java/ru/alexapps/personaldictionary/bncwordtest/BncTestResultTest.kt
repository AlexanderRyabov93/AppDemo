package ru.alexapps.personaldictionary.bncwordtest

import com.nhaarman.mockitokotlin2.spy
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import ru.alexapps.personaldictionary.data.models.BncWord
import ru.alexapps.personaldictionary.data.models.EnglishLevel
import java.util.*

class BncTestResultTest {

    @Test
    fun `no words test result`() {
        val testResult = BncTestResult(emptyArray(), emptyArray(), Date())
        assertEquals(0, testResult.categoryList.size)
        assertEquals(0, testResult.getExtrapolationKnownWords())
    }

    @Test
    fun `only known words test result`() {
        val knownWords = arrayOf(
            BncWord(1, "test1_1", 1),
            BncWord(2, "test2_1", 1),
            BncWord(3, "test3_2", 2),
            BncWord(4, "test4_3", 3),
            BncWord(5, "test5_5", 5),
            BncWord(6, "test6_5", 5),
            BncWord(7, "test7_5", 5),
        )
        val testResult = BncTestResult(knownWords, emptyArray(), Date())
        assertEquals(4, testResult.categoryList.size)
        assertEquals(4000, testResult.getExtrapolationKnownWords())
    }

    @Test
    fun `only unknown words test result`() {
        val unknownWords = arrayOf(
            BncWord(1, "test1_1", 1),
            BncWord(2, "test2_1", 1),
            BncWord(3, "test3_2", 2),
            BncWord(4, "test4_3", 3),
            BncWord(5, "test5_5", 5),
            BncWord(6, "test6_5", 5),
            BncWord(7, "test7_5", 5),
        )
        val testResult = BncTestResult(emptyArray(), unknownWords, Date())
        assertEquals(4, testResult.categoryList.size)
        assertEquals(0, testResult.getExtrapolationKnownWords())
    }

    @Test
    fun `known and unknown words test result`() {
        val knownWords = arrayOf(
            BncWord(1, "test1_1", 1),
            BncWord(2, "test2_1", 1),
            BncWord(3, "test3_2", 2),
            BncWord(4, "test4_3", 3),
            BncWord(5, "test5_5", 5),
            BncWord(6, "test6_5", 5),
            BncWord(7, "test7_5", 5),
        )
        val unknownWords = arrayOf(
            BncWord(8, "test8_1", 1),
            BncWord(9, "test9_1", 1),
            BncWord(10, "test10_3", 3),
            BncWord(11, "test11_3", 3),
            BncWord(12, "test12_5", 5),
            BncWord(13, "test13_1", 1),
            BncWord(14, "test14_6", 6),
        )
        val testResult = BncTestResult(knownWords, unknownWords, Date())
        assertEquals(5, testResult.categoryList.size)

        var category: BncTestResult.DifficultyCategory? = testResult.categoryList.find { it.difficulty == 1 }
        assertNotNull(category)
        assertEquals(400, category!!.extrapolationKnownWords)

        category = testResult.categoryList.find { it.difficulty == 2 }
        assertNotNull(category)
        assertEquals(1000, category!!.extrapolationKnownWords)

        category = testResult.categoryList.find { it.difficulty == 3 }
        assertNotNull(category)
        assertEquals(333, category!!.extrapolationKnownWords)

        category = testResult.categoryList.find { it.difficulty == 5 }
        assertNotNull(category)
        assertEquals(750, category!!.extrapolationKnownWords)

        category = testResult.categoryList.find { it.difficulty == 6 }
        assertNotNull(category)
        assertEquals(0, category!!.extrapolationKnownWords)
        
        assertEquals(2483, testResult.getExtrapolationKnownWords())
    }

    @Test
    fun `getEnglishLevel should return A1 value is 0`() {
        val knownWords = 0
        val testResult: BncTestResult =  spy(BncTestResult(emptyArray(), emptyArray(), Date(), 1))
        Mockito.`when`(testResult.getExtrapolationKnownWords()).thenReturn(knownWords)
        assertEquals(EnglishLevel.A1, testResult.getEnglishLevel())
    }
    @Test
    fun `getEnglishLevel should return A1 value is 1199`() {
        val knownWords = 1199
        val testResult: BncTestResult =  spy(BncTestResult(emptyArray(), emptyArray(), Date(), 1))
        Mockito.`when`(testResult.getExtrapolationKnownWords()).thenReturn(knownWords)
        assertEquals(EnglishLevel.A1, testResult.getEnglishLevel())
    }
    @Test
    fun `getEnglishLevel should return A2 value is 1200`() {
        val knownWords = 1200
        val testResult: BncTestResult =  spy(BncTestResult(emptyArray(), emptyArray(), Date(), 1))
        Mockito.`when`(testResult.getExtrapolationKnownWords()).thenReturn(knownWords)
        assertEquals(EnglishLevel.A2, testResult.getEnglishLevel())
    }
    @Test
    fun `getEnglishLevel should return A2 value is 1999`() {
        val knownWords = 1999
        val testResult: BncTestResult =  spy(BncTestResult(emptyArray(), emptyArray(), Date(), 1))
        Mockito.`when`(testResult.getExtrapolationKnownWords()).thenReturn(knownWords)
        assertEquals(EnglishLevel.A2, testResult.getEnglishLevel())
    }
    @Test
    fun `getEnglishLevel should return B1 value is 2000`() {
        val knownWords = 2000
        val testResult: BncTestResult =  spy(BncTestResult(emptyArray(), emptyArray(), Date(), 1))
        Mockito.`when`(testResult.getExtrapolationKnownWords()).thenReturn(knownWords)
        assertEquals(EnglishLevel.B1, testResult.getEnglishLevel())
    }
    @Test
    fun `getEnglishLevel should return B1 value is 3999`() {
        val knownWords = 3999
        val testResult: BncTestResult =  spy(BncTestResult(emptyArray(), emptyArray(), Date(), 1))
        Mockito.`when`(testResult.getExtrapolationKnownWords()).thenReturn(knownWords)
        assertEquals(EnglishLevel.B1, testResult.getEnglishLevel())
    }
    @Test
    fun `getEnglishLevel should return B2 value is 4000`() {
        val knownWords = 4000
        val testResult: BncTestResult =  spy(BncTestResult(emptyArray(), emptyArray(), Date(), 1))
        Mockito.`when`(testResult.getExtrapolationKnownWords()).thenReturn(knownWords)
        assertEquals(EnglishLevel.B2, testResult.getEnglishLevel())
    }
    @Test
    fun `getEnglishLevel should return B2 value is 6999`() {
        val knownWords = 6999
        val testResult: BncTestResult =  spy(BncTestResult(emptyArray(), emptyArray(), Date(), 1))
        Mockito.`when`(testResult.getExtrapolationKnownWords()).thenReturn(knownWords)
        assertEquals(EnglishLevel.B2, testResult.getEnglishLevel())
    }
    @Test
    fun `getEnglishLevel should return C1 value is 7000`() {
        val knownWords = 7000
        val testResult: BncTestResult =  spy(BncTestResult(emptyArray(), emptyArray(), Date(), 1))
        Mockito.`when`(testResult.getExtrapolationKnownWords()).thenReturn(knownWords)
        assertEquals(EnglishLevel.C1, testResult.getEnglishLevel())
    }
}