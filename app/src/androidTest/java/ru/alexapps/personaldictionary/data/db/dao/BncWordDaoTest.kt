package ru.alexapps.personaldictionary.data.db.dao

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import ru.alexapps.personaldictionary.data.db.WordDatabase
import ru.alexapps.personaldictionary.data.db.entities.BncVocabularyTestEntity
import ru.alexapps.personaldictionary.data.db.entities.BncWordEntity
import ru.alexapps.personaldictionary.data.db.entities.VocabularyTestResultsEntity
import ru.alexapps.personaldictionary.data.db.entities.VocabularyTestWithVocabularyTestResult
import java.util.*

@RunWith(AndroidJUnit4::class)
class BncWordDaoTest {
    private lateinit var db: WordDatabase
    private lateinit var dao: BncWordDao
    private val words = arrayOf(
        BncWordEntity(1, "id 1 difficulty 1", 1),
        BncWordEntity(2, "id 2 difficulty 1", 1),
        BncWordEntity(3, "id 3 difficulty 1", 1),
        BncWordEntity(4, "id 4 difficulty 1", 1),
        BncWordEntity(5, "id 5 difficulty 2", 2),
        BncWordEntity(6, "id 6 difficulty 2", 2),
        BncWordEntity(7, "id 7 difficulty 3", 3),
    )

    @get:Rule
    val exceptionRule: ExpectedException = ExpectedException.none()

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            WordDatabase::class.java
        ).build()
        dao = db.bncWordDao()
        runBlocking {
            words.forEach { dao.insertBncWord(it) }
        }
    }
    @After
    fun closeDb() {
        db.close()
    }
    //Нельзя никак проверить рандомность, поэтому проверяем что просто работает
    @Test
    fun getRandomBncWords_should_return_correct_word() {
        runBlocking {
            val words = dao.getRandomBncWords(3, 1)
            assertTrue(words.size == 1)
            assertEquals(BncWordEntity(7, "id 7 difficulty 3", 3), words[0])
        }
    }
    @Test
    fun insertTestResult_should_insert() {
        runBlocking {
            val testResult = VocabularyTestResultsEntity(1, words[0].id, true, 0)
            val date = Date()
            val id = dao.insertTestResults(date, listOf( testResult))
            assertEquals(testResult.id, id)
            val fromDb = dao.getAllVocabularyTests()
            assertTrue(fromDb.size == 1)
            assertEquals(testResult, fromDb[0].testResults[0].testResult)
            assertEquals(words[0], fromDb[0].testResults[0].bncWordEntity)
            assertEquals(date, fromDb[0].bncTest.testDate)
        }
    }
    @Test
    fun insertTestResult_should_throw_error_wrong_id() {
        runBlocking {
            val wrongId = -1L
            val testResult = VocabularyTestResultsEntity(1, wrongId,true, 1)
            exceptionRule.expect(SQLiteConstraintException::class.java)
            exceptionRule.expectMessage("FOREIGN KEY constraint failed")
            dao.insertTestResults(Date(), listOf(testResult))
        }

    }
    @Test
    fun getVocabularyTestById_should_return_correct_test() {
        runBlocking {
            val date = Date()
            val id = dao.insertTestResults(date, emptyList())
            val fromDb = dao.getVocabularyTestById(id)
            assertEquals(VocabularyTestWithVocabularyTestResult(BncVocabularyTestEntity(id, date), emptyList()), fromDb)
        }
    }
    @Test
    fun getVocabularyTestById_should_return_null() {
        runBlocking {
            val fromDb = dao.getVocabularyTestById(1)
            assertNull(fromDb)
        }
    }




}