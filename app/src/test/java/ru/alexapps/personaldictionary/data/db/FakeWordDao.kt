package ru.alexapps.personaldictionary.data.db

import ru.alexapps.personaldictionary.data.db.dao.WordDao
import ru.alexapps.personaldictionary.data.db.entities.*

open class FakeWordDao(private val initialData: List<WordWithSense>) : WordDao() {
    override fun getAllWords(): List<WordWithSense> {
        return initialData
    }

    override suspend fun getWords(word: String): List<WordWithSense> {
        return listOf(initialData.get(0))
    }

    override suspend fun getWordsBySubstring(substring: String, limit: Int): List<WordWithSense> {
        return initialData
    }

    override fun getAllSimpleWords(): List<SimpleWordWithSemanticCategory> {
        TODO("Not yet implemented")
    }

    override suspend fun getSimpleWord(word: String): List<SimpleWordWithSemanticCategory> {
        TODO("Not yet implemented")
    }

    override suspend fun insertWord(word: WordEntity): Long {
        return 1
    }

    override suspend fun insertSimpleWord(simpleWordEntity: SimpleWordEntity): Long {
        TODO("Not yet implemented")
    }

    override suspend fun insertDefinitions(definitions: List<Definition>) {}

    override suspend fun insertExamples(examples: List<Example>) {}

    override suspend fun insertSemanticCategory(semanticCategory: SemanticCategoryEntity): Long {
        return 1
    }

    override suspend fun insertSense(senseEntity: SenseEntity): Long {
        return 1
    }

    override suspend fun updateWord(word: WordEntity) {}

    override suspend fun insertWordWithData(
        wordWithSense: WordWithSense
    ): Long {
        return 1
    }
}