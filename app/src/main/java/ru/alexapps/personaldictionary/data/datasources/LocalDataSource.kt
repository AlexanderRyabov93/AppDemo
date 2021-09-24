package ru.alexapps.personaldictionary.data.datasources

import ru.alexapps.personaldictionary.data.models.SemanticCategory
import ru.alexapps.personaldictionary.data.models.Word

interface LocalDataSource: BaseDataSource {

    suspend fun saveWord(word: Word): Long

    suspend fun  saveSemanticCategory(semanticCategory: SemanticCategory): Long

    suspend fun updateWord(word: Word)
}