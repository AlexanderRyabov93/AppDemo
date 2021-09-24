package ru.alexapps.personaldictionary.testHelpers

import ru.alexapps.personaldictionary.data.models.LexicalCategory
import ru.alexapps.personaldictionary.data.models.Sense
import ru.alexapps.personaldictionary.data.models.Word
import ru.alexapps.personaldictionary.data.network.retrofitmodels.*

object OxfordWordData {
    val definitions1 = listOf("def1_1", "def1_2")
    val examples1 = listOf("example1_1", "example1_2")
    val definitions2 = listOf("def2_1", "def2_2")
    val examples2 = listOf("example2_1", "example2_2")
    val definitions3 = listOf("def3_1", "def3_2")
    val examples3 = listOf("example3_1", "example3_2")
    val definitions4 = listOf("def4_1", "def4_2")
    val examples4 = listOf("example4_1", "example4_2")
    val oxfordEntries1 = listOf(
        OxfordEntry(
            listOf(
                OxfordSense(definitions1, examples1.map { OxfordExample(it) }),
                OxfordSense(definitions2, examples2.map { OxfordExample(it) })
            )
        ),
        OxfordEntry(
            listOf(
                OxfordSense(definitions3, examples3.map { OxfordExample(it) })
            )
        )
    )
    val oxfordEntries2 = listOf(
        OxfordEntry(listOf(OxfordSense(definitions4, examples4.map { OxfordExample(it) })))
    )
    val lexicalEntry1 =
        OxfordLexicalEntry(oxfordEntries1, "en", OxfordLexicalCategory("NOUN", "NOUN"), "test1")
    val lexicalEntry2 =
        OxfordLexicalEntry(oxfordEntries2, "en", OxfordLexicalCategory("VERB", "VERB"), "test2")

    val oxfordWord = OxfordWord(
        "test",
        listOf(
            OxfordWordResult(
                "test", "en", listOf(lexicalEntry1),
                "headword",
                "test"
            ),
            OxfordWordResult(
                "test1", "en", listOf(lexicalEntry2),
                "headword",
                "test1"
            )

        )
    )
    val words = arrayOf(
        Word(
            0,
            oxfordWord.results[0].lexicalEntries[0].text,
            arrayOf(
                Sense(
                    0,
                    oxfordEntries1[0].senses[0].definitions.toTypedArray(),
                    oxfordEntries1[0].senses[0].examples.map { it.text }.toTypedArray()
                ),
                Sense(
                    0,
                    oxfordEntries1[0].senses[1].definitions.toTypedArray(),
                    oxfordEntries1[0].senses[1].examples.map { it.text }.toTypedArray()
                ),
                Sense(
                    0,
                    oxfordEntries1[1].senses[0].definitions.toTypedArray(),
                    oxfordEntries1[1].senses[0].examples.map { it.text }.toTypedArray()
                )
            ),
            LexicalCategory.NOUN,
            null,
            null

        ),
        Word(
            0,
            oxfordWord.results[1].lexicalEntries[0].text,
            arrayOf(
                Sense(
                    0,
                    oxfordEntries2[0].senses[0].definitions.toTypedArray(),
                    oxfordEntries2[0].senses[0].examples.map { it.text }.toTypedArray()
                )
            ),
            LexicalCategory.VERB,
            null,
            null

        )
    )
}