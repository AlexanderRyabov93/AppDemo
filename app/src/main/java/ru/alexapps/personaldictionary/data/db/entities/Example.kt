package ru.alexapps.personaldictionary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_EXAMPLE
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_ID
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_SENSE_ID
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_WORD_ID
import ru.alexapps.personaldictionary.data.db.DbConstants.TABLE_EXAMPLES

@Entity(
    tableName = TABLE_EXAMPLES,
    foreignKeys = [ForeignKey(
        entity = SenseEntity::class,
        parentColumns = [COLUMN_ID],
        childColumns = [COLUMN_SENSE_ID]
    )]
)
data class Example(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = COLUMN_ID) val id: Long,
    @ColumnInfo(name = COLUMN_EXAMPLE) val example: String,
    @ColumnInfo(name = COLUMN_SENSE_ID) var senseId: Long
)