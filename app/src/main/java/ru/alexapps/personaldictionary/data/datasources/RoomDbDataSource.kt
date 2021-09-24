package ru.alexapps.personaldictionary.data.datasources

import androidx.annotation.VisibleForTesting
import ru.alexapps.personaldictionary.data.db.WordDatabase
import ru.alexapps.personaldictionary.data.db.dao.WordDao
import ru.alexapps.personaldictionary.data.db.entities.*
import ru.alexapps.personaldictionary.data.models.SemanticCategory
import ru.alexapps.personaldictionary.data.models.Sense
import ru.alexapps.personaldictionary.data.models.Word
import ru.alexapps.personaldictionary.utils.Result
import java.util.*

open class RoomDbDataSource(db: WordDatabase) : LocalDataSource {
    private val wordDao = db.wordDao()
    private val bncWordDao = db.bncWordDao()

    override suspend fun saveWord(word: Word): Long {
        return wordDao.insertWordWithData(
            wordToWordWithSense(word, word.saveDate ?: Date())
        )
    }

    override suspend fun saveSemanticCategory(semanticCategory: SemanticCategory): Long {
        return wordDao.insertSemanticCategory(
            semanticCategoryToSemanticCategoryEntity(semanticCategory)
        )
    }

    override suspend fun updateWord(word: Word) {
        wordDao.updateWord(wordToWordEntity(word, word.saveDate ?: Date()))
    }

    override suspend fun getWords(wordToFind: String): Result<List<Word>> {
        val wordsWithSense = wordDao.getWords(wordToFind)
        return Result.Success(wordsWithSense.map { wordWithSenseToWord(it) })
    }

    override suspend fun getWordsBySubstring(substring: String, limit: Int): Result<List<String>> {
        val wordsWithData = wordDao.getWordsBySubstring(substring, limit)
        val bncWordLimit = limit - wordsWithData.size
        val bncWords = bncWordDao.getWordsBySubstring(substring, bncWordLimit)
        val result: MutableList<String> = mutableListOf()
        result.addAll(wordsWithData.map { wordWithData -> wordWithData.word.word })
        result.addAll(bncWords.map { bncWordEntity -> bncWordEntity.word })
        return Result.Success(result)
    }

    @VisibleForTesting
    fun wordToWordEntity(word: Word, date: Date): WordEntity =
        WordEntity(word.id, word.word, word.lexicalCategory, date, word.semanticCategory?.id)

    @VisibleForTesting
    fun semanticCategoryToSemanticCategoryEntity(semanticCategory: SemanticCategory): SemanticCategoryEntity =
        SemanticCategoryEntity(
            semanticCategory.id,
            semanticCategory.title,
            semanticCategory.description
        )

    @VisibleForTesting
    fun semanticCategoryEntityToSemanticCategory(semanticCategoryEntity: SemanticCategoryEntity): SemanticCategory =
        SemanticCategory(
            semanticCategoryEntity.id,
            semanticCategoryEntity.title,
            semanticCategoryEntity.description
        )

    @VisibleForTesting
    fun senseWithDataToSense(senseWithData: SenseWithData): Sense =
        Sense(
            senseWithData.sense.id,
            senseWithData.definitions.map { definition -> definition.definition }.toTypedArray(),
            senseWithData.examples.map { example -> example.example }.toTypedArray()
        )

    @VisibleForTesting
    fun senseToSenseWithData(sense: Sense): SenseWithData =
        SenseWithData(
            SenseEntity(sense.id, 0),
            sense.definitions.map { Definition(0, it, 0) },
            sense.examples.map { Example(0, it, 0) }
        )

    @VisibleForTesting
    fun wordWithSenseToWord(wordWithData: WordWithSense): Word =
        Word(
            wordWithData.word.id,
            wordWithData.word.word,
            wordWithData.senses.map { senseWithData -> senseWithDataToSense(senseWithData) }
                .toTypedArray(),
            wordWithData.word.lexicalCategory,
            if (wordWithData.semanticCategory == null) null else semanticCategoryEntityToSemanticCategory(
                wordWithData.semanticCategory
            ),
            wordWithData.word.saveDate
        )

    @VisibleForTesting
    fun wordToWordWithSense(word: Word, date: Date): WordWithSense =
        WordWithSense(
            wordToWordEntity(word, date),
            word.senses.map { senseToSenseWithData(it) },
            if (word.semanticCategory == null) null else semanticCategoryToSemanticCategoryEntity(
                word.semanticCategory!!
            )
        )
}