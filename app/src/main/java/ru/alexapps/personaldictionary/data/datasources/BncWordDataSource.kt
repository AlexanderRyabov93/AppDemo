package ru.alexapps.personaldictionary.data.datasources

import ru.alexapps.personaldictionary.bncwordtest.BncTestResult
import ru.alexapps.personaldictionary.data.models.BncWord
import ru.alexapps.personaldictionary.utils.Result

interface BncWordDataSource {

    suspend fun getRandomBncWords(minDifficulty: Int, maxDifficulty: Int, limit: Int): Array<BncWord>

    suspend fun saveTestResult(testResult: BncTestResult): Long

    suspend fun getTestResultById(id: Long): Result<BncTestResult>

}