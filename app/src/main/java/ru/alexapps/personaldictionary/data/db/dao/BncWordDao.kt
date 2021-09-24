package ru.alexapps.personaldictionary.data.db.dao

import androidx.annotation.VisibleForTesting
import androidx.room.*
import ru.alexapps.personaldictionary.data.db.DbConstants
import ru.alexapps.personaldictionary.data.db.entities.*
import java.util.*

@Dao
abstract class BncWordDao {

    @Query("SELECT * FROM ${DbConstants.TABLE_BNC_WORDS} WHERE ${DbConstants.COLUMN_ID} IN (SELECT ${DbConstants.COLUMN_ID} FROM ${DbConstants.TABLE_BNC_WORDS} WHERE ${DbConstants.COLUMN_DIFFICULTY} = :difficulty ORDER BY RANDOM() LIMIT :limit)")
    abstract suspend fun getRandomBncWords(difficulty: Int, limit: Int): List<BncWordEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    protected abstract suspend fun insertTestResult(testResultsEntity: VocabularyTestResultsEntity): Long
    @VisibleForTesting
    @Insert(onConflict = OnConflictStrategy.ABORT)
    protected abstract suspend fun insertTestResults(testResults: List<VocabularyTestResultsEntity>)

    @Query("SELECT * FROM ${DbConstants.TABLE_VOCABULARY_TEST_RESULTS}")
    protected abstract suspend fun getAllTestResults(): List<VocabularyTestResultWithBncWord>

    @Query("SELECT * FROM ${DbConstants.TABLE_BNC_WORDS_TESTS} WHERE ${DbConstants.COLUMN_ID} = :id")
    abstract suspend fun getVocabularyTestById(id: Long): VocabularyTestWithVocabularyTestResult?

    @Query("SELECT * FROM ${DbConstants.TABLE_BNC_WORDS_TESTS}")
    abstract suspend fun getAllVocabularyTests(): List<VocabularyTestWithVocabularyTestResult>

    @VisibleForTesting
    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insertBncWord(word: BncWordEntity): Long

    @Query("SELECT * FROM ${DbConstants.TABLE_BNC_WORDS} WHERE ${DbConstants.COLUMN_WORD} LIKE '%' || :substring || '%' LIMIT :limit")
    abstract suspend fun getWordsBySubstring(substring: String, limit: Int): List<BncWordEntity>

    @Transaction
    open suspend fun insertTestResults(testDate: Date, testResults: List<VocabularyTestResultsEntity>): Long {
        val testId = insertTest(BncVocabularyTestEntity(0, testDate))
        testResults.forEach { it.testId = testId }
        insertTestResults(testResults)
        return testId
    }
    @Insert(onConflict = OnConflictStrategy.ABORT)
    protected abstract suspend fun insertTest(test: BncVocabularyTestEntity): Long


}