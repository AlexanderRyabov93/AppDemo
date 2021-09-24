package ru.alexapps.personaldictionary.data.db.typeconverters

import androidx.room.TypeConverter
import ru.alexapps.personaldictionary.data.models.LexicalCategory
import ru.alexapps.personaldictionary.utils.convertStringToLexicalCategory
import java.lang.IllegalArgumentException

class LexicalCategoryConverter {
    @TypeConverter
    fun lexicalCategoryToString(lexicalCategory: LexicalCategory): String {
        return lexicalCategory.name
    }
    @TypeConverter
    fun stringToLexicalCategory(categoryName: String): LexicalCategory =
        convertStringToLexicalCategory(categoryName)
}