package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.BibleReadingEntity
import org.deiverbum.app.core.database.model.entity.MassReadingEntity
import org.deiverbum.app.core.model.data.missae.MissaeLectionum

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class MassReadingWithAll(
    @Embedded
    val entity: MassReadingEntity,

    @Relation(
        parentColumn = "readingFK",
        entityColumn = "readingID",
        entity = BibleReadingEntity::class
    )
    var lectura: BibleReadingWithBook
)

fun MassReadingWithAll.asExternalModel() = MissaeLectionum(
    lectura.asExternalModelBook(),
    lectura.lectura.cita,
    entity.tema,
    lectura.lectura.texto,
    entity.orden,
)