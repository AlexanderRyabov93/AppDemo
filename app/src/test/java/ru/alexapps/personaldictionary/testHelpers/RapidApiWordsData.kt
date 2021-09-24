package ru.alexapps.personaldictionary.testHelpers

import ru.alexapps.personaldictionary.data.models.LexicalCategory
import ru.alexapps.personaldictionary.data.models.Sense
import ru.alexapps.personaldictionary.data.models.Word
import ru.alexapps.personaldictionary.data.network.retrofitmodels.wordsapi.RapidApiWord
import ru.alexapps.personaldictionary.data.network.retrofitmodels.wordsapi.RapidApiWordResult

object RapidApiWordsData {

    val rapidApiWord = RapidApiWord(
        "test",
        listOf(
            RapidApiWordResult("definition1", "noun", listOf("example1", "example2")),
            RapidApiWordResult("definition2", "verb", emptyList()),
            RapidApiWordResult("definition3", "adjective", null)
        )
    )
    val words = arrayOf(
        Word(
            0,
            "test",
            arrayOf(Sense(0, arrayOf("definition1"), arrayOf("example1", "example2"))),
            LexicalCategory.NOUN,
            null,
            null
        ),
        Word(
            0,
            "test",
            arrayOf(Sense(0, arrayOf("definition2"), emptyArray())),
            LexicalCategory.VERB,
            null,
            null
        ),
        Word(
            0,
            "test",
            arrayOf(Sense(0, arrayOf("definition3"), emptyArray())),
            LexicalCategory.ADJECTIVE,
            null,
            null
        ),
    )
}