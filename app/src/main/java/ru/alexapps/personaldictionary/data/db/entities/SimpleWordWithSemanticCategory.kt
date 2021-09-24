package ru.alexapps.personaldictionary.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation
import ru.alexapps.personaldictionary.data.db.DbConstants

data class SimpleWordWithSemanticCategory(
    @Embedded val simpleWord: SimpleWordEntity,
    @Relation(
        parentColumn = DbConstants.COLUMN_SEMANTIC_CATEGORY_ID,
        entityColumn = DbConstants.COLUMN_ID
    )
    val semanticCategory: SemanticCategoryEntity?

) {
}