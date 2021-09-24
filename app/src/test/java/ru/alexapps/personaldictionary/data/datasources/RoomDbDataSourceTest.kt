package ru.alexapps.personaldictionary.data.datasources


import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import ru.alexapps.personaldictionary.data.db.FakeWordDao
import ru.alexapps.personaldictionary.data.db.WordDatabase
import ru.alexapps.personaldictionary.data.db.dao.BncWordDao
import ru.alexapps.personaldictionary.data.db.dao.WordDao
import ru.alexapps.personaldictionary.data.db.entities.*
import ru.alexapps.personaldictionary.data.models.LexicalCategory
import ru.alexapps.personaldictionary.data.models.SemanticCategory
import ru.alexapps.personaldictionary.data.models.Sense
import ru.alexapps.personaldictionary.data.models.Word
import ru.alexapps.personaldictionary.utils.Result
import java.util.*

class RoomDbDataSourceTest {
    private lateinit var initialData: List<WordWithSense>
    private lateinit var dao: WordDao
    private lateinit var bncWordDao: BncWordDao
    private lateinit var dataSource: RoomDbDataSource

    @Before
    fun setup() {

        initialData = listOf(
            WordWithSense(
                WordEntity(1, "test1", LexicalCategory.PREPOSITION, Date(), null),
                emptyList(),
                null
            ),
            WordWithSense(
                WordEntity(2, "test2", LexicalCategory.PREPOSITION, Date(), null),
                emptyList(),
                null
            ),
            WordWithSense(
                WordEntity(3, "test3", LexicalCategory.PREPOSITION, Date(), null),
                emptyList(),
                null
            )
        )
        val db = mock(WordDatabase::class.java)

        val fakeDao = FakeWordDao(initialData)
        dao = spy(fakeDao)
        bncWordDao = mock()
        `when`(db.wordDao()).thenReturn(dao)
        `when`(db.bncWordDao()).thenReturn(bncWordDao)
        dataSource = spy(RoomDbDataSource(db))
    }

    @Test
    fun `wordToWordEntity should return correctEntity`() {
        val word = Word(1, "test1", emptyArray(),  LexicalCategory.NOUN, null, null)
        val date = Date()
        assertEquals(
            WordEntity(word.id, word.word, word.lexicalCategory, date, null),
            dataSource.wordToWordEntity(word, date)
        )
    }

    @Test
    fun `semanticCategoryToSemanticCategoryEntity should return correct value`() {
        val semanticCategory = SemanticCategory(1, "title", null)
        assertEquals(
            SemanticCategoryEntity(
                semanticCategory.id,
                semanticCategory.title,
                semanticCategory.description
            ), dataSource.semanticCategoryToSemanticCategoryEntity(semanticCategory)
        )
    }

    @Test
    fun `semanticCategoryEntityToSemanticCategory should return correct value`() {
        val semanticCategoryEntity = SemanticCategoryEntity(1, "title", null)
        assertEquals(
            SemanticCategory(
                semanticCategoryEntity.id,
                semanticCategoryEntity.title,
                semanticCategoryEntity.description
            ),
            dataSource.semanticCategoryEntityToSemanticCategory(semanticCategoryEntity)
        )
    }
    @Test
    fun `wordWithDataToWord should return correct value`() {
        val wordWithSense = WordWithSense(
            WordEntity(1, "test", LexicalCategory.NOUN, Date(), 1),
            listOf(SenseWithData(
                SenseEntity(1, 1),
                listOf(Definition(1, "definition",1)),
                listOf(Example(1, "example", 1))
            )),
            SemanticCategoryEntity(1, "semanticCategory", "description")
        )
        assertEquals(
            Word(
                wordWithSense.word.id,
                wordWithSense.word.word,
                arrayOf(
                    Sense(
                        wordWithSense.senses[0].sense.id,
                        wordWithSense.senses[0].definitions.map { it.definition }.toTypedArray(),
                        wordWithSense.senses[0].examples.map { it.example }.toTypedArray()
                    )
                ),
                wordWithSense.word.lexicalCategory,
                SemanticCategory(wordWithSense.semanticCategory!!.id, wordWithSense.semanticCategory!!.title, wordWithSense.semanticCategory!!.description),
                wordWithSense.word.saveDate
            ),
            dataSource.wordWithSenseToWord(wordWithSense)
        )
    }
    @Test
    fun `saveWord should call dao`() = runBlocking {
        val word = Word(
            1,
            "word",
            arrayOf(
                Sense(1, arrayOf("definition"), arrayOf("example"))
            ),
            LexicalCategory.NOUN,
            null,
            Date())
        val wordWithSense = WordWithSense(
            WordEntity(
                word.id,
                word.word,
                word.lexicalCategory,
                word.saveDate!!,
                null
            ),
            listOf(
                SenseWithData(
                    SenseEntity(word.senses[0].id, 0),
                    listOf(Definition(0, word.senses[0].definitions[0], 0)),
                    listOf(Example(0, word.senses[0].examples[0], 0))
                )
            ),
            null
        )
        val result = dataSource.saveWord(word)
        verify(dao).insertWordWithData(
            wordWithSense
        )
        assertEquals(1, result)
    }
    @Test
    fun `saveSemanticCategory should call dao`() = runBlocking {
        val semanticCategory = SemanticCategory(1, "test", "testDescription")
        val result = dataSource.saveSemanticCategory(semanticCategory)
        verify(dao).insertSemanticCategory(SemanticCategoryEntity(semanticCategory.id, semanticCategory.title, semanticCategory.description))
        assertEquals(1, result)
    }
    @Test
    fun `updateWord should call dao`() = runBlocking {
        val word = Word(
            1,
            "word",
            arrayOf(
                Sense(1, arrayOf("definition"), arrayOf("example"))
            ),
            LexicalCategory.NOUN,
            null,
            Date())
        dataSource.updateWord(word)
        verify(dao).updateWord(
            WordEntity(
                word.id,
                word.word,
                word.lexicalCategory,
                word.saveDate!!,
                null
            )
        )
    }
    @Test
    fun `getWord should call dao`() = runBlocking {
        val wordToFind = "test"
        val result = dataSource.getWords(wordToFind)
        verify(dao).getWords(wordToFind)
        assertTrue(result is Result.Success)
        assertEquals(listOf(dataSource.wordWithSenseToWord(initialData[0])), (result as Result.Success).data)
    }
    @Test
    fun `getWordsBySubstring should call dao`() = runBlocking {
        val substring = "test"
        val limit = 2
        `when`(bncWordDao.getWordsBySubstring(any(), any())).thenReturn(emptyList())
        val result = dataSource.getWordsBySubstring(substring, limit)
        verify(dao).getWordsBySubstring(substring, limit)
        assertTrue(result is Result.Success)
        assertArrayEquals(initialData.map { wordWithData -> wordWithData.word.word }.toTypedArray(), (result as Result.Success).data.toTypedArray())
    }
}