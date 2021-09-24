package ru.alexapps.personaldictionary.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_ID
import ru.alexapps.personaldictionary.data.db.DbConstants.COLUMN_SENSE_ID

data class SenseWithData(
    @Embedded val sense: SenseEntity,
    @Relation(
        entity = Definition::class,
        parentColumn = COLUMN_ID,
        entityColumn = COLUMN_SENSE_ID
    )
    val definitions: List<Definition>,
    @Relation(
        entity = Example::class,
        parentColumn = COLUMN_ID,
        entityColumn = COLUMN_SENSE_ID

    )
    val examples: List<Example>
)