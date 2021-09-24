package ru.alexapps.personaldictionary.data.network.retrofitmodels

data class OxfordWord(
    val query: String,
    val results: List<OxfordWordResult>
)