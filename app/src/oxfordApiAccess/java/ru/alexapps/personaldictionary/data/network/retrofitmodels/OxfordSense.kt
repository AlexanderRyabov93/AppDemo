package ru.alexapps.personaldictionary.data.network.retrofitmodels

data class OxfordSense(
    val definitions: List<String>,
    val examples: List<OxfordExample>
)