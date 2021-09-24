package ru.alexapps.personaldictionary.data.datasources

import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import ru.alexapps.personaldictionary.bncwordtest.BncTestResult
import ru.alexapps.personaldictionary.data.db.dao.BncWordDao
import ru.alexapps.personaldictionary.data.db.entities.*
import ru.alexapps.personaldictionary.data.models.BncWord
import ru.alexapps.personaldictionary.exceptions.InvalidIdException
import ru.alexapps.personaldictionary.utils.Result
import java.util.*

class RoomBncDataSourceTest {
    private lateinit var dao: BncWordDao
    private lateinit var dataSource: RoomBncDataSource

    @Before
    fun setup() {
        dao = mock()
        dataSource = spy(RoomBncDataSource(dao))
    }

    @Test
    fun `getRandomBncWords should call dao and return correct array`() {
        val minDifficulty = 1
        val maxDifficulty = 3
        val limit = 5

        runBlocking {
            `when`(dao.getRandomBncWords(minDifficulty, limit)).thenReturn(
                listOf(
                    BncWordEntity(
                        1,
                        "test1_${minDifficulty}",
                        minDifficulty
                    ), BncWordEntity(2, "test2_${minDifficulty}", minDifficulty)
                )
            )
            `when`(dao.getRandomBncWords(maxDifficulty, limit)).thenReturn(
                listOf(
                    BncWordEntity(
                        3,
                        "test3_${maxDifficulty}",
                        maxDifficulty
                    ), BncWordEntity(4, "test4_${maxDifficulty}", maxDifficulty)
                )
            )
            `when`(dao.getRandomBncWords(2, limit)).thenReturn(emptyList())
            val result = dataSource.getRandomBncWords(minDifficulty, maxDifficulty, limit)
            verify(dao, times(1)).getRandomBncWords(minDifficulty, limit)
            verify(dao, times(1)).getRandomBncWords(2, limit)
            verify(dao, times(1)).getRandomBncWords(maxDifficulty, limit)
            verify(dataSource, times(4)).bncWordEntityToBncWord(any())
            assertArrayEquals(
                arrayOf(
                    BncWord(
                        1,
                        "test1_${minDifficulty}",
                        minDifficulty
                    ),
                    BncWord(
                        2,
                        "test2_${minDifficulty}",
                        minDifficulty
                    ),
                    BncWord(
                        3,
                        "test3_${maxDifficulty}",
                        maxDifficulty
                    ),
                    BncWord(
                        4,
                        "test4_${maxDifficulty}",
                        maxDifficulty
                    )
                ), result
            )
        }
    }

    @Test
    fun `saveTestResult should call dao`() {
        val testResult = BncTestResult(emptyArray(), emptyArray(), Date(), 1)
        val id = 2L
        runBlocking {
            `when`(dao.insertTestResults(any(), any())).thenReturn(id)
            val insertedId = dataSource.saveTestResult(testResult)
            verify(dataSource, times(1)).bncTestResultToVocabularyTestResultEntity(testResult)
            verify(dao, times(1)).insertTestResults(any(), any())
            assertEquals(id, insertedId)
        }

    }

    @Test
    fun `getTestResultById should return error cause wrong id`() {
        val id = 3L
        runBlocking {
            `when`(dao.getVocabularyTestById(id)).thenReturn(null)
            val result = dataSource.getTestResultById(id)
            assertTrue(result is Result.Error)
            assertTrue((result as Result.Error).exception is InvalidIdException)
        }
    }

    @Test
    fun `getTestResultById should return testResult`() {
        val id = 3L
        val bncVocabularyTestEntity = BncVocabularyTestEntity(1, Date())
        val vocabularyTestResults = listOf(
            VocabularyTestResultWithBncWord(
                VocabularyTestResultsEntity(1, 1, true, 1),
                BncWordEntity(1, "know this word", 2)
            ),
            VocabularyTestResultWithBncWord(
                VocabularyTestResultsEntity(2, 2, false, 1),
                BncWordEntity(2, "don't know this word", 3)
            )
        )
        val data =
            VocabularyTestWithVocabularyTestResult(bncVocabularyTestEntity, vocabularyTestResults)
        runBlocking {
            `when`(dao.getVocabularyTestById(id)).thenReturn(data)
            val result = dataSource.getTestResultById(id)
            assertTrue(result is Result.Success)
            verify(dataSource, times(1)).vocabularyTestWithVocabularyTestResultToBncTestResult(data)

        }

    }

    @Test
    fun `bncWordEntityToBncWord should return correct value`() {
        val entity = BncWordEntity(1, "test", 2)
        assertEquals(
            BncWord(entity.id, entity.word, entity.difficulty),
            dataSource.bncWordEntityToBncWord(entity)
        )
    }

    @Test
    fun `bncTestResultToVocabularyTestResultEntity should return empty list for empty result`() {
        val testResult = BncTestResult(emptyArray(), emptyArray(), Date())
        val entities = dataSource.bncTestResultToVocabularyTestResultEntity(testResult)
        assertEquals(0, entities.size)
    }

    @Test
    fun `bncTestResultToVocabularyTestResultEntity should return correct entities`() {
        val testResult = BncTestResult(
            arrayOf(BncWord(1, "know this word", 2)),
            arrayOf(BncWord(2, "don't know this word", 3)),
            Date()
        )
        val entities = dataSource.bncTestResultToVocabularyTestResultEntity(testResult)
        assertArrayEquals(
            arrayOf(
                VocabularyTestResultsEntity(0, 1, true, 0),
                VocabularyTestResultsEntity(0, 2, false, 0)
            ), entities.toTypedArray()
        )
    }

    @Test
    fun `vocabularyTestWithVocabularyTestResultToBncTestResult should return correctValue`() {
        val bncVocabularyTestEntity = BncVocabularyTestEntity(1, Date())
        val vocabularyTestResults = listOf(
            VocabularyTestResultWithBncWord(
                VocabularyTestResultsEntity(1, 1, true, 1),
                BncWordEntity(1, "know this word", 2)
            ),
            VocabularyTestResultWithBncWord(
                VocabularyTestResultsEntity(2, 2, false, 1),
                BncWordEntity(2, "don't know this word", 3)
            )
        )
        val data =
            VocabularyTestWithVocabularyTestResult(bncVocabularyTestEntity, vocabularyTestResults)
        val testResults = dataSource.vocabularyTestWithVocabularyTestResultToBncTestResult(data)
        assertArrayEquals(
            arrayOf(BncWord(1, "know this word", 2)),
            testResults.knownWords
        )
        assertArrayEquals(
            arrayOf(BncWord(2, "don't know this word", 3)),
            testResults.unKnownWords
        )
        assertEquals(bncVocabularyTestEntity.testDate, testResults.testDate)
        assertEquals(bncVocabularyTestEntity.id, testResults.id)
        verify(dataSource, times(2)).bncWordEntityToBncWord(any())
    }

}