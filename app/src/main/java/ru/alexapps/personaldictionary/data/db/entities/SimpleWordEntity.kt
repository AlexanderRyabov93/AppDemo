package ru.alexapps.personaldictionary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.alexapps.personaldictionary.data.db.DbConstants
import ru.alexapps.personaldictionary.data.db.DbConstants.TABLE_SIMPLE_WORDS
import java.util.*

@Entity(tableName = TABLE_SIMPLE_WORDS,
    foreignKeys = [ForeignKey(
        entity = SemanticCategoryEntity::class,
        parentColumns = [DbConstants.COLUMN_ID],
        childColumns = [DbConstants.COLUMN_SEMANTIC_CATEGORY_ID]
    )])
data class SimpleWordEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = DbConstants.COLUMN_ID) val id: Long,
    @ColumnInfo(name = DbConstants.COLUMN_WORD) val word: String,
    @ColumnInfo(name = DbConstants.COLUMN_SAVE_DATE) val saveDate: Date,
    @ColumnInfo(name = DbConstants.COLUMN_SEMANTIC_CATEGORY_ID) val semanticCategoryId: Long?
)