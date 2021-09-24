package ru.alexapps.personaldictionary.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.alexapps.personaldictionary.bncwordtest.BncTestResult
import ru.alexapps.personaldictionary.data.datasources.BncWordDataSource
import ru.alexapps.personaldictionary.data.models.BncWord
import ru.alexapps.personaldictionary.utils.Result

class BncWordRepository(private val dataSource: BncWordDataSource) : IBncWordRepository {

    override suspend fun getRandomBncWords(
        minDifficulty: Int,
        maxDifficulty: Int,
        limit: Int
    ): Array<BncWord> {
        return withContext(Dispatchers.IO) {
            dataSource.getRandomBncWords(
                minDifficulty,
                maxDifficulty,
                limit
            )
        }
    }

    override suspend fun getTestResultById(id: Long): BncTestResult {
        return withContext(Dispatchers.IO) {
            val result = dataSource.getTestResultById(id)
            if (result is Result.Error) {
                throw result.exception
            }
            (result as Result.Success).data
        }

    }

    override suspend fun saveTestResult(testResult: BncTestResult): Long {
        return dataSource.saveTestResult(testResult)
    }
}