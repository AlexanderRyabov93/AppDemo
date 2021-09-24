package ru.alexapps.personaldictionary.utils

import androidx.annotation.VisibleForTesting
import ru.alexapps.personaldictionary.data.models.Sense
import ru.alexapps.personaldictionary.data.models.Word
import ru.alexapps.personaldictionary.data.network.retrofitmodels.wordsapi.RapidApiWord
import ru.alexapps.personaldictionary.data.network.retrofitmodels.wordsapi.RapidApiWordResult
import javax.inject.Inject

class RapidApiWordToWords @Inject constructor(): Mapper<RapidApiWord, List<@JvmSuppressWildcards Word>> {
    override fun map(data: RapidApiWord): List<Word> {
        return data.results.map {
            Word(
                0,
                data.word,
                getSenses(it),
                convertStringToLexicalCategory(it.partOfSpeech),
                null,
                null
            )
        }
    }
    @VisibleForTesting
    fun getSenses(wordResult: RapidApiWordResult): Array<Sense> {
        val examples: Array<String> =
            if (wordResult.examples == null) emptyArray() else wordResult.examples.toTypedArray()
        return arrayOf(
            Sense(
                0,
                arrayOf(wordResult.definition),
                examples
            )
        )
    }
}