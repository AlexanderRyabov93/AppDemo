package ru.alexapps.personaldictionary.data.db.dao

import androidx.room.*
import ru.alexapps.personaldictionary.data.db.DbConstants
import ru.alexapps.personaldictionary.data.db.entities.*

@Dao
abstract class WordDao {
    @Transaction
    @Query("SELECT * FROM ${DbConstants.TABLE_WORDS}")
    abstract fun getAllWords(): List<WordWithSense>

    @Transaction
    @Query("SELECT * FROM ${DbConstants.TABLE_WORDS} WHERE ${DbConstants.COLUMN_WORD} = :word")
    abstract suspend fun getWords(word: String): List<WordWithSense>

    @Transaction
    @Query("SELECT * FROM ${DbConstants.TABLE_WORDS} WHERE ${DbConstants.COLUMN_WORD} LIKE '%' || :substring || '%' LIMIT :limit")
    abstract suspend fun getWordsBySubstring(substring: String, limit: Int): List<WordWithSense>

    @Transaction
    @Query("SELECT * FROM ${DbConstants.TABLE_SIMPLE_WORDS}")
    abstract fun getAllSimpleWords(): List<SimpleWordWithSemanticCategory>

    @Transaction
    @Query("SELECT * FROM ${DbConstants.TABLE_SIMPLE_WORDS} WHERE ${DbConstants.COLUMN_WORD} = :word")
    abstract suspend fun getSimpleWord(word: String): List<SimpleWordWithSemanticCategory>

    @Transaction
    open suspend fun insertSimpleWordWithSemanticCategory(simpleWord: SimpleWordWithSemanticCategory): Long {
        var simpleWordEntity = simpleWord.simpleWord
        if(simpleWord.semanticCategory != null && simpleWord.semanticCategory.id == 0) {
            val semanticCategoryId = insertSemanticCategory(simpleWord.semanticCategory)
            simpleWordEntity = SimpleWordEntity(simpleWord.simpleWord.id, simpleWord.simpleWord.word, simpleWord.simpleWord.saveDate, semanticCategoryId)
        }
        return insertSimpleWord(simpleWordEntity)
    }

    @Transaction
    open suspend fun insertWordWithData(
        wordWithSense: WordWithSense
    ): Long {
        val wordId = insertWord(wordWithSense.word)
        for(sense in wordWithSense.senses) {
            sense.sense.wordId = wordId
            val senseId = insertSense(sense.sense)
            sense.definitions.forEach { it.senseId = senseId }
            sense.examples.forEach { it.senseId = senseId }
            insertDefinitions(sense.definitions)
            insertExamples(sense.examples)
        }
        return wordId
    }

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insertWord(word: WordEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insertSimpleWord(simpleWordEntity: SimpleWordEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insertDefinitions(definitions: List<Definition>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insertExamples(examples: List<Example>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insertSemanticCategory(semanticCategory: SemanticCategoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insertSense(senseEntity: SenseEntity): Long

    @Update
    abstract suspend fun updateWord(word: WordEntity)
}