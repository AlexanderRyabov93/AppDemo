package ru.alexapps.personaldictionary.data.network.retrofitmodels.wordsapi

data class RapidApiWordResult(
    val definition: String,
    val partOfSpeech: String,
    val examples: List<String>?
) {
}