package ru.alexapps.personaldictionary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexapps.personaldictionary.data.db.DbConstants
import java.util.*

@Entity(
    tableName = DbConstants.TABLE_VOCABULARY_TEST_RESULTS,
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = BncWordEntity::class,
            parentColumns = [DbConstants.COLUMN_ID],
            childColumns = [DbConstants.COLUMN_BNC_WORD_ID]
        ),
        androidx.room.ForeignKey(
            entity = BncVocabularyTestEntity::class,
            parentColumns = [DbConstants.COLUMN_ID],
            childColumns = [DbConstants.COLUMN_TEST_ID]
        )
    ]
)
data class VocabularyTestResultsEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = DbConstants.COLUMN_ID) val id: Long,
    @ColumnInfo(name = DbConstants.COLUMN_BNC_WORD_ID) val bncWordId: Long,
    @ColumnInfo(name = DbConstants.COLUMN_TEST_PASSED) val isPassed: Boolean,
    @ColumnInfo(name = DbConstants.COLUMN_TEST_ID) var testId: Long
)
