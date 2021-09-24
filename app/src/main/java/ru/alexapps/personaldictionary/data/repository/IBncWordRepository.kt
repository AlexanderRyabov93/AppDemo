package ru.alexapps.personaldictionary.data.repository

import ru.alexapps.personaldictionary.bncwordtest.BncTestResult
import ru.alexapps.personaldictionary.data.models.BncWord

interface IBncWordRepository {

    suspend fun getRandomBncWords(minDifficulty: Int, maxDifficulty: Int, limit: Int): Array<BncWord>

    suspend fun getTestResultById(id: Long): BncTestResult

    suspend fun saveTestResult(testResult: BncTestResult): Long
}