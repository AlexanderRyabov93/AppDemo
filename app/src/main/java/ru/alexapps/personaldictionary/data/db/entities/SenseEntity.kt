package ru.alexapps.personaldictionary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.alexapps.personaldictionary.data.db.DbConstants

@Entity(
    tableName = DbConstants.TABLE_SENSES,
    foreignKeys = [ForeignKey(
        entity = WordEntity::class,
        parentColumns = [DbConstants.COLUMN_ID],
        childColumns = [DbConstants.COLUMN_WORD_ID]
    )]
)
data class SenseEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = DbConstants.COLUMN_ID) val id: Long,
    @ColumnInfo(name = DbConstants.COLUMN_WORD_ID) var wordId: Long
)