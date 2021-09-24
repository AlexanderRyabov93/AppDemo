package ru.alexapps.personaldictionary.utils

import ru.alexapps.personaldictionary.data.models.LexicalCategory
import java.util.*

fun convertStringToLexicalCategory(categoryName: String): LexicalCategory {
    return when (categoryName.toUpperCase(Locale.ROOT)) {
        LexicalCategory.NOUN.name -> LexicalCategory.NOUN
        LexicalCategory.ADJECTIVE.name -> LexicalCategory.ADJECTIVE
        LexicalCategory.ADVERB.name -> LexicalCategory.ADVERB
        LexicalCategory.PREPOSITION.name -> LexicalCategory.PREPOSITION
        LexicalCategory.VERB.name -> LexicalCategory.VERB
        else -> throw IllegalArgumentException("Illegal categoryName")
    }
}

fun stringArrayToString(array: Array<String>): String {
    return array.joinToString("\n", "", "",
        -1,
        "..."
    ) { it.capitalize(Locale.ROOT)}
}