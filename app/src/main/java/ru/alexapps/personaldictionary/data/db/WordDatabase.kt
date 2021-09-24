package ru.alexapps.personaldictionary.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.alexapps.personaldictionary.data.db.dao.BncWordDao
import ru.alexapps.personaldictionary.data.db.dao.WordDao
import ru.alexapps.personaldictionary.data.db.entities.*
import ru.alexapps.personaldictionary.data.db.typeconverters.DateConverter
import ru.alexapps.personaldictionary.data.db.typeconverters.LexicalCategoryConverter

@Database(
    entities = [
        WordEntity::class,
        Definition::class,
        Example::class,
        SemanticCategoryEntity::class,
        SenseEntity::class,
        BncWordEntity::class,
        VocabularyTestResultsEntity::class,
        BncVocabularyTestEntity::class,
        SimpleWordEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(LexicalCategoryConverter::class, DateConverter::class)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun bncWordDao(): BncWordDao
}