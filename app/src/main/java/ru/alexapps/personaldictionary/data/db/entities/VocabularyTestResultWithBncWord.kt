package ru.alexapps.personaldictionary.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_BNC_WORD_ID
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_ID

data class VocabularyTestResultWithBncWord(
    @Embedded val testResult: VocabularyTestResultsEntity,
    @Relation(
        parentColumn = COLUMN_BNC_WORD_ID,
        entityColumn = COLUMN_ID
    )
    val bncWordEntity: BncWordEntity
)