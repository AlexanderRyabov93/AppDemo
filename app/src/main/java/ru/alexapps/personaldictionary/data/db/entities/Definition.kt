package ru.alexapps.personaldictionary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.alexapps.personaldictionary.data.db.DbConstants
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_DEFINITION
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_ID
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_SENSE_ID
import ru.alexapps.personaldictionary.data.db.DbConstants.TABLE_DEFINITIONS

@Entity(
    tableName = TABLE_DEFINITIONS,
    foreignKeys = [ForeignKey(
        entity = SenseEntity::class,
        parentColumns = [COLUMN_ID],
        childColumns = [COLUMN_SENSE_ID]
    )]
    )
data class Definition(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = COLUMN_ID) val id: Long,
                      @ColumnInfo(name = COLUMN_DEFINITION) val definition: String,
                      @ColumnInfo(name = COLUMN_SENSE_ID) var senseId: Long)