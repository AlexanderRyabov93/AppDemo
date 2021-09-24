package ru.alexapps.personaldictionary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_DESCRIPTION
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_ID
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_TITLE
import ru.alexapps.personaldictionary.data.db.DbConstants.TABLE_SEMANTIC_CATEGORIES

@Entity(tableName = TABLE_SEMANTIC_CATEGORIES)
data class SemanticCategoryEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = COLUMN_ID) val id: Int,
    @ColumnInfo(name = COLUMN_TITLE) val title: String,
    @ColumnInfo(name = COLUMN_DESCRIPTION) val description: String? = null
)
