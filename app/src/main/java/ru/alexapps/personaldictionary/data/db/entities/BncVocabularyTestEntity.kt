package ru.alexapps.personaldictionary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexapps.personaldictionary.data.db.DbConstants
import java.util.*

@Entity(tableName = DbConstants.TABLE_BNC_WORDS_TESTS)
data class BncVocabularyTestEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = DbConstants.COLUMN_ID) val id: Long,
    @ColumnInfo(name = DbConstants.COLUMN_TEST_DATE) val testDate: Date,
)