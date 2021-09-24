package ru.alexapps.personaldictionary.data.network.retrofitmodels.wordsapi

data class RapidApiWord(
    val word: String,
    val results: List<RapidApiWordResult>
)