package ru.alexapps.personaldictionary.data.network.retrofitmodels

data class OxfordLexicalEntry(
    val entries: List<OxfordEntry>,
    val language: String,
    val lexicalCategory: OxfordLexicalCategory,
    val text: String
)