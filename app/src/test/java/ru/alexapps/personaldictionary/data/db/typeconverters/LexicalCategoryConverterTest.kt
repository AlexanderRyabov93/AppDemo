package ru.alexapps.personaldictionary.data.db.typeconverters

import org.junit.Assert.*
import org.junit.Test
import ru.alexapps.personaldictionary.data.models.LexicalCategory
import java.lang.RuntimeException

class LexicalCategoryConverterTest {
    private val converter = LexicalCategoryConverter()

    @Test
    fun `LexicalCategory to String`() {
        assertEquals("NOUN", converter.lexicalCategoryToString(LexicalCategory.NOUN))
    }
    @Test
    fun `NOUN to LexicalCategory`() {
        assertEquals(LexicalCategory.NOUN, converter.stringToLexicalCategory("NOUN"))
    }
    @Test
    fun `ADVERB to LexicalCategory`() {
        assertEquals(LexicalCategory.ADVERB, converter.stringToLexicalCategory("ADVERB"))
    }
    @Test
    fun `ADJECTIVE to LexicalCategory`() {
        assertEquals(LexicalCategory.ADJECTIVE, converter.stringToLexicalCategory("ADJECTIVE"))
    }
    @Test
    fun `VERB to LexicalCategory`() {
        assertEquals(LexicalCategory.VERB, converter.stringToLexicalCategory("VERB"))
    }
    @Test
    fun `PREPOSITION to LexicalCategory`() {
        assertEquals(LexicalCategory.PREPOSITION, converter.stringToLexicalCategory("PREPOSITION"))
    }
    @Test(expected = RuntimeException::class)
    fun `wrong string to lexicalCategory should throw exception`() {
        converter.stringToLexicalCategory("WRONG_STRING")
    }


}