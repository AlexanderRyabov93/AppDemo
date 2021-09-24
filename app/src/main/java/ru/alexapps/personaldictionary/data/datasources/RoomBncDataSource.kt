package ru.alexapps.personaldictionary.data.datasources

import androidx.annotation.VisibleForTesting
import ru.alexapps.personaldictionary.bncwordtest.BncTestResult
import ru.alexapps.personaldictionary.data.db.dao.BncWordDao
import ru.alexapps.personaldictionary.data.db.entities.BncWordEntity
import ru.alexapps.personaldictionary.data.db.entities.VocabularyTestResultsEntity
import ru.alexapps.personaldictionary.data.db.entities.VocabularyTestWithVocabularyTestResult
import ru.alexapps.personaldictionary.data.models.BncWord
import ru.alexapps.personaldictionary.exceptions.InvalidIdException
import ru.alexapps.personaldictionary.utils.Result

class RoomBncDataSource(private val bncWordDao: BncWordDao) : BncWordDataSource {

    override suspend fun getRandomBncWords(
        minDifficulty: Int,
        maxDifficulty: Int,
        limit: Int
    ): Array<BncWord> {
        val words: MutableList<BncWordEntity> = mutableListOf()
        for (difficulty in minDifficulty..maxDifficulty) {
            words.addAll(bncWordDao.getRandomBncWords(difficulty, limit))
        }
        return words.map { bncWordEntityToBncWord(it) }.toTypedArray()
    }

    override suspend fun saveTestResult(testResult: BncTestResult): Long {
        return bncWordDao.insertTestResults(
            testResult.testDate,
            bncTestResultToVocabularyTestResultEntity(testResult)
        )
    }

    override suspend fun getTestResultById(id: Long): Result<BncTestResult> {
        val result: VocabularyTestWithVocabularyTestResult = bncWordDao.getVocabularyTestById(id)
            ?: return Result.Error(InvalidIdException("Invalid test result id"))
        return Result.Success(vocabularyTestWithVocabularyTestResultToBncTestResult(result))

    }

    @VisibleForTesting
    fun bncWordEntityToBncWord(entity: BncWordEntity): BncWord =
        BncWord(entity.id, entity.word, entity.difficulty)

    @VisibleForTesting
    fun bncTestResultToVocabularyTestResultEntity(testResult: BncTestResult): List<VocabularyTestResultsEntity> {
        val result: MutableList<VocabularyTestResultsEntity> = mutableListOf()
        testResult.knownWords.forEach { result.add(VocabularyTestResultsEntity(0, it.id, true, 0)) }
        testResult.unKnownWords.forEach {
            result.add(
                VocabularyTestResultsEntity(
                    0,
                    it.id,
                    false,
                    0
                )
            )
        }
        return result
    }

    @VisibleForTesting
    fun vocabularyTestWithVocabularyTestResultToBncTestResult(data: VocabularyTestWithVocabularyTestResult): BncTestResult {
        val knownWords: MutableList<BncWord> = mutableListOf()
        val unKnownWords: MutableList<BncWord> = mutableListOf()
        data.testResults.forEach { res ->
            val bncWord = bncWordEntityToBncWord(res.bncWordEntity)
            if (res.testResult.isPassed) {
                knownWords.add(bncWord)
            } else {
                unKnownWords.add(bncWord)
            }
        }
        return BncTestResult(
            knownWords.toTypedArray(),
            unKnownWords.toTypedArray(),
            data.bncTest.testDate,
            data.bncTest.id
        )
    }

}