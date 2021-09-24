package ru.alexapps.personaldictionary.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_ID
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_SEMANTIC_CATEGORY_ID
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_WORD_ID

data class WordWithSense(
    @Embedded val word: WordEntity,
    @Relation(
        entity = SenseEntity::class,
        parentColumn = COLUMN_ID,
        entityColumn = COLUMN_WORD_ID
    )
    val senses: List<SenseWithData>,
    @Relation(
        parentColumn = COLUMN_SEMANTIC_CATEGORY_ID,
        entityColumn = COLUMN_ID
    )
    val semanticCategory: SemanticCategoryEntity?
)