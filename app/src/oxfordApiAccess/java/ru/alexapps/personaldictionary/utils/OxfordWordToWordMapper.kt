package ru.alexapps.personaldictionary.utils

import androidx.annotation.VisibleForTesting
import ru.alexapps.personaldictionary.data.models.Sense
import ru.alexapps.personaldictionary.data.models.Word
import ru.alexapps.personaldictionary.data.network.retrofitmodels.*
import javax.inject.Inject

class OxfordWordToWordMapper @Inject constructor(): Mapper<OxfordWord, List<@JvmSuppressWildcards Word>> {

    override fun map(data: OxfordWord): List<Word> {
        val listWords: MutableList<Word> = mutableListOf()
        data.results.forEach { listWords.addAll(oxfordWordResultToWords(it)) }
        return listWords
    }

    @VisibleForTesting
    fun oxfordWordResultToWords(oxfordWordResult: OxfordWordResult): List<Word> =
        oxfordWordResult.lexicalEntries.map { lexicalEntryToWord(it) }


    @VisibleForTesting
    fun lexicalEntryToWord(oxfordLexicalEntry: OxfordLexicalEntry): Word =
        Word(
            0,
            oxfordLexicalEntry.text,
            oxfordEntriesToSenses(oxfordLexicalEntry.entries),
            convertStringToLexicalCategory(oxfordLexicalEntry.lexicalCategory.text),
            null,
            null
        );

    @VisibleForTesting
    fun oxfordEntriesToSenses(oxfordEntries: List<OxfordEntry>): Array<Sense> {
        val listSenses: MutableList<Sense> = mutableListOf()
        oxfordEntries.forEach {
            it.senses.forEach {
                listSenses.add(oxfordSenseToSense(it))
            }
        }
        return listSenses.toTypedArray()
    }

    @VisibleForTesting
    fun oxfordSenseToSense(oxfordSense: OxfordSense): Sense {
        return Sense(
            0,
            oxfordSense.definitions.toTypedArray(),
            oxfordSense.examples.map { it.text }.toTypedArray()
        )
    }
}