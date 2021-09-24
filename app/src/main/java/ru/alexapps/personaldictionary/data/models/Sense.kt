package ru.alexapps.personaldictionary.data.models

data class Sense(
    val id: Long,
    val definitions: Array<String>,
    val examples: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Sense) return false

        if (id != other.id) return false
        if (!definitions.contentEquals(other.definitions)) return false
        if (!examples.contentEquals(other.examples)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + definitions.contentHashCode()
        result = 31 * result + examples.contentHashCode()
        return result
    }
}