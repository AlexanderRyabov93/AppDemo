package ru.alexapps.personaldictionary.data.models

import java.util.*

data class Word(
    val id: Long,
    val word: String,
    val senses: Array<Sense>,
    val lexicalCategory: LexicalCategory,
    var semanticCategory: SemanticCategory?,
    var saveDate: Date?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Word) return false

        if (id != other.id) return false
        if (word != other.word) return false
        if (!senses.contentEquals(other.senses)) return false
        if (lexicalCategory != other.lexicalCategory) return false
        if (semanticCategory != other.semanticCategory) return false
        if (saveDate != other.saveDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + word.hashCode()
        result = 31 * result + senses.contentHashCode()
        result = 31 * result + lexicalCategory.hashCode()
        result = 31 * result + (semanticCategory?.hashCode() ?: 0)
        result = 31 * result + (saveDate?.hashCode() ?: 0)
        return result
    }

}