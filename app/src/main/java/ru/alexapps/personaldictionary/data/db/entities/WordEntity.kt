package ru.alexapps.personaldictionary.data.db.entities

import androidx.room.*
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_ID
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_LEXICAL_CATEGORY
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_SAVE_DATE
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_SEMANTIC_CATEGORY_ID
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_WORD
import ru.alexapps.personaldictionary.data.db.DbConstants.TABLE_WORDS
import ru.alexapps.personaldictionary.data.models.LexicalCategory
import java.util.*

@Entity(
    tableName = TABLE_WORDS,
    foreignKeys = [ForeignKey(
        entity = SemanticCategoryEntity::class,
        parentColumns = [COLUMN_ID],
        childColumns = [COLUMN_SEMANTIC_CATEGORY_ID]
    )]
)
data class WordEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = COLUMN_ID) val id: Long,
    @ColumnInfo(name = COLUMN_WORD) val word: String,
    @ColumnInfo(name = COLUMN_LEXICAL_CATEGORY) val lexicalCategory: LexicalCategory,
    @ColumnInfo(name = COLUMN_SAVE_DATE) val saveDate: Date,
    @ColumnInfo(name = COLUMN_SEMANTIC_CATEGORY_ID) val semanticCategoryId: Int?
)