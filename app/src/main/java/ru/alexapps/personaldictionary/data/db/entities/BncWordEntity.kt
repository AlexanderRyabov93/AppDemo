package ru.alexapps.personaldictionary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexapps.personaldictionary.data.db.DbConstants

@Entity(tableName = DbConstants.TABLE_BNC_WORDS)
data class BncWordEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = DbConstants.COLUMN_ID) val id: Long,
    @ColumnInfo(name = DbConstants.COLUMN_WORD) val word: String,
    @ColumnInfo(name = DbConstants.COLUMN_DIFFICULTY) val difficulty: Int
)