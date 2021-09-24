package ru.alexapps.personaldictionary.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation
import ru.alexapps.personaldictionary.data.db.DbConstants

data class VocabularyTestWithVocabularyTestResult(
    @Embedded val bncTest: BncVocabularyTestEntity,
    @Relation(
        parentColumn = DbConstants.COLUMN_ID,
        entityColumn = DbConstants.COLUMN_TEST_ID,
        entity = VocabularyTestResultsEntity::class
    )
    val testResults: List<VocabularyTestResultWithBncWord>
)