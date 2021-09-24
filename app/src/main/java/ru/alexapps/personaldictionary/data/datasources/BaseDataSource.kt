package ru.alexapps.personaldictionary.data.datasources

import ru.alexapps.personaldictionary.data.models.Word
import ru.alexapps.personaldictionary.utils.Result

interface BaseDataSource {

    suspend fun getWords(wordToFind: String): Result<List<Word>>

    suspend fun getWordsBySubstring(substring: String, limit: Int): Result<List<String>>
}