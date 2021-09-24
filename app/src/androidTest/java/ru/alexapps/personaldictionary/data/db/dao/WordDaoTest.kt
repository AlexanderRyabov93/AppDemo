package ru.alexapps.personaldictionary.data.db.dao

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import ru.alexapps.personaldictionary.data.db.WordDatabase
import ru.alexapps.personaldictionary.data.db.entities.*
import ru.alexapps.personaldictionary.data.models.LexicalCategory
import java.util.*


@RunWith(AndroidJUnit4::class)
class WordDaoTest {
    private lateinit var db: WordDatabase
    private lateinit var dao: WordDao


    @get:Rule
    val exceptionRule: ExpectedException = ExpectedException.none()

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            WordDatabase::class.java
        ).build()
        dao = db.wordDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun read_empty_db_should_return_empty_list() {
        val words = dao.getAllWords()
        assertEquals(0, words.size)
    }

    @Test
    fun insertWord() = runBlocking {
        val word = WordEntity(1, "home", LexicalCategory.NOUN, Date(), null)

        val senseEntity = SenseEntity(1, word.id)
        val definitions = arrayOf(
            Definition(1, "definition1", senseEntity.id),
            Definition(2, "definition2", senseEntity.id),
            Definition(3, "definition3", senseEntity.id)
        )
        val examples = arrayOf(
            Example(1, "example1", senseEntity.id),
            Example(2, "example2", senseEntity.id)
        )
        val senses = listOf(SenseWithData(senseEntity, definitions.toList(), examples.toList()))
        val wordWithSense = WordWithSense(
            word,
            senses,
            null
        )
        dao.insertWordWithData(wordWithSense)
        val words = dao.getAllWords()
        assertEquals(1, words.size)
        val loadedWord = words[0]
        assertEquals(word, loadedWord.word)
        assertArrayEquals(senses.toTypedArray(), loadedWord.senses.toTypedArray())
    }

    @Test
    fun insert_definition_with_wrong_sense_id_should_throw_exception() = runBlocking {
        val wrongId = 54321L
        val definition = Definition(1, "definition", wrongId)
        exceptionRule.expect(SQLiteConstraintException::class.java)
        exceptionRule.expectMessage("FOREIGN KEY constraint failed")
        val id = dao.insertDefinitions(listOf(definition))
    }

    @Test
    fun insert_example_with_wrong_sense_id_should_throw_exception() = runBlocking {
        val wrongId = 54321L
        val example = Example(1, "example", wrongId)
        exceptionRule.expect(SQLiteConstraintException::class.java)
        exceptionRule.expectMessage("FOREIGN KEY constraint failed")
        val id = dao.insertExamples(listOf(example))
    }

    @Test
    fun insert_sense_with_wrong_word_id_should_throw_exception() = runBlocking {
        val wrongId = 54321L
        val sense = SenseEntity(1, wrongId)
        exceptionRule.expect(SQLiteConstraintException::class.java)
        exceptionRule.expectMessage("FOREIGN KEY constraint failed")
        val id = dao.insertSense(sense)
    }

    @Test
    fun insert_word_with_wrong_semantic_category_id_should_throw_exception() = runBlocking {
        val wrongId = 54321
        val word = WordEntity(1, "home", LexicalCategory.NOUN, Date(), wrongId)
        exceptionRule.expect(SQLiteConstraintException::class.java)
        exceptionRule.expectMessage("FOREIGN KEY constraint failed")
        val id = dao.insertWord(word)
    }

    @Test
    fun insert_word_with_correct_semantic_category_id() = runBlocking {
        val semanticCategory = SemanticCategoryEntity(1, "testSemanticCategory")
        val word = WordEntity(1, "home", LexicalCategory.NOUN, Date(), semanticCategory.id)
        dao.insertSemanticCategory(semanticCategory)
        dao.insertWord(word)
        val words = dao.getAllWords()
        assertEquals(1, words.size)
        val loadedWordWithData = words[0]
        assertEquals(semanticCategory, loadedWordWithData.semanticCategory)
    }

    @Test
    fun update_word() = runBlocking {
        val semanticCategory = SemanticCategoryEntity(1, "testSemanticCategory")
        val word = WordEntity(1, "home", LexicalCategory.NOUN, Date(), null)
        dao.insertSemanticCategory(semanticCategory)
        dao.insertWord(word)
        dao.updateWord(word.copy(semanticCategoryId = semanticCategory.id))
        val words = dao.getAllWords()
        assertEquals(1, words.size)
        val loadedWordWithData = words[0]
        assertEquals(semanticCategory, loadedWordWithData.semanticCategory)
    }

    @Test
    fun getWord_should_return_word() = runBlocking {
        val str = "home"
        val word = WordEntity(1, str, LexicalCategory.NOUN, Date(), null)
        dao.insertWord(word)
        val wordsFromDb = dao.getWords(str)
        assertNotNull(wordsFromDb)
        assertEquals(1, wordsFromDb.size)
        assertEquals(word, wordsFromDb[0].word)
    }

    @Test
    fun getWord_should_return_null() = runBlocking {
        val str = "home"
        val word = WordEntity(1, str, LexicalCategory.NOUN, Date(), null)
        dao.insertWord(word)
        //Try to get wrong word
        val wordsFromDb = dao.getWords(str + "WRONG")
        assertNotNull(wordsFromDb)
        assertEquals(0, wordsFromDb.size)
    }

    @Test
    fun getWordBySubstring_should_return_correct_words() = runBlocking {
        val rootWord = "rootWord";
        val word = WordEntity(1, rootWord, LexicalCategory.NOUN, Date(), null)
        val postfixWord = WordEntity(2, "${rootWord}_POSTFIX", LexicalCategory.NOUN, Date(), null)
        val prefixWord = WordEntity(3, "PREFIX_${rootWord}", LexicalCategory.NOUN, Date(), null)
        val wrongWord = WordEntity(4, rootWord.substring(1), LexicalCategory.NOUN, Date(), null)
        dao.insertWord(word)
        dao.insertWord(postfixWord)
        dao.insertWord(prefixWord)
        dao.insertWord(wrongWord)
        val wordsFromDb = dao.getWordsBySubstring(rootWord, 10)
        assertNotNull(wordsFromDb)
        assertEquals(3, wordsFromDb.size)
        assertArrayEquals(
            arrayOf(word, postfixWord, prefixWord),
            wordsFromDb.map { wordWithData -> wordWithData.word }.toTypedArray()
        )
    }

    @Test
    fun getWordBySubstring_check_limit() = runBlocking {
        val rootWord = "rootWord";
        val word = WordEntity(1, rootWord, LexicalCategory.NOUN, Date(), null)
        val postfixWord = WordEntity(2, "${rootWord}_POSTFIX", LexicalCategory.NOUN, Date(), null)
        dao.insertWord(word)
        dao.insertWord(postfixWord)
        val wordsFromDbLimit2 = dao.getWordsBySubstring(rootWord, 2)
        assertNotNull(wordsFromDbLimit2)
        assertEquals(2, wordsFromDbLimit2.size)
        val wordsFromDbLimit1 = dao.getWordsBySubstring(rootWord, 1)
        assertNotNull(wordsFromDbLimit1)
        assertEquals(1, wordsFromDbLimit1.size)
    }

    @Test
    fun getAllSimpleWords_should_return_empty_list() {
        runBlocking {
            val words = dao.getAllSimpleWords()
            assertEquals(0, words.size)
        }
    }

    @Test
    fun getAllSimpleWords_should_return_correct_list() {
        runBlocking {
            val simpleWordEntity = SimpleWordEntity(1, "test", Date(), null)
            dao.insertSimpleWord(simpleWordEntity)
            val words = dao.getAllSimpleWords()
            assertEquals(1, words.size)
            assertEquals(simpleWordEntity, words[0].simpleWord)
            assertNull(words[0].semanticCategory)
        }
    }

    @Test
    fun getSimpleWord_should_return_empty_list() {
        runBlocking {
            val words = dao.getSimpleWord("test")
            assertEquals(0, words.size)
        }
    }

    @Test
    fun getSimpleWord_should_return_correct_word() {
        runBlocking {
            val simpleWordEntity1 = SimpleWordEntity(1, "test1", Date(), null)
            val simpleWordEntity2 = SimpleWordEntity(2, "test2", Date(), null)
            dao.insertSimpleWord(simpleWordEntity1)
            dao.insertSimpleWord(simpleWordEntity2)
            val words = dao.getSimpleWord("test2")
            assertEquals(1, words.size)
            assertEquals(simpleWordEntity2, words[0].simpleWord)
            assertNull(words[0].semanticCategory)
        }
    }

    @Test
    fun insertSimpleWordWithSemanticCategory_should_insert_with_semanticCategory() {
        runBlocking {
            val simpleWordWithSemanticCategory = SimpleWordWithSemanticCategory(
                SimpleWordEntity(1, "test", Date(), 1),
                SemanticCategoryEntity(0, "test_semantic_category")
            )
            dao.insertSimpleWordWithSemanticCategory(simpleWordWithSemanticCategory)
            val words = dao.getAllSimpleWords()
            assertEquals(1, words.size)
            assertEquals(simpleWordWithSemanticCategory.simpleWord, words[0].simpleWord)
            assertEquals(SemanticCategoryEntity(1, "test_semantic_category"), words[0].semanticCategory)
        }
    }
    @Test
    fun insertSimpleWordWithSemanticCategory_should_throw_exception() {
        runBlocking {
            val simpleWordWithSemanticCategory = SimpleWordWithSemanticCategory(
                SimpleWordEntity(1, "test", Date(), 1),
                null
            )
            exceptionRule.expect(SQLiteConstraintException::class.java)
            exceptionRule.expectMessage("FOREIGN KEY constraint failed")
            dao.insertSimpleWordWithSemanticCategory(simpleWordWithSemanticCategory)
        }
    }
}