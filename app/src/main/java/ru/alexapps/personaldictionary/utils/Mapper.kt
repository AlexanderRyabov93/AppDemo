package ru.alexapps.personaldictionary.utils

interface Mapper<Input, Output> {

    fun map(data: Input): Output
}