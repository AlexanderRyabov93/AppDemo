package ru.alexapps.personaldictionary.data.repository

import ru.alexapps.personaldictionary.data.models.SemanticCategory
import ru.alexapps.personaldictionary.data.models.Word

interface IWordRepository {

    suspend fun fetchWord(wordToFind: String): List<Word>

    suspend fun getWordsBySubstring(substring: String): List<String>

    suspend fun saveWord(word: Word): Long

    suspend fun  saveSemanticCategory(semanticCategory: SemanticCategory): Long

    suspend fun updateWord(word: Word)
}