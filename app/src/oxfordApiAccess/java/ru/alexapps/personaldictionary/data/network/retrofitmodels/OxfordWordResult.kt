package ru.alexapps.personaldictionary.data.network.retrofitmodels

data class OxfordWordResult(
    val id: String,
    val language: String,
    val lexicalEntries: List<OxfordLexicalEntry>,
    val type: String,
    val word: String
)