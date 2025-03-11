package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.BibleHomilyJoinEntity
import org.deiverbum.app.core.database.model.entity.BibleHomilyThemeEntity
import org.deiverbum.app.core.database.model.entity.HomilyEntity
import org.deiverbum.app.core.model.data.traditio.BibleComment
import org.deiverbum.app.core.model.data.traditio.Homily

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class BibleHomilyWithAll(
    @Embedded
    val bibliaLecturaEntity: BibleHomilyJoinEntity,

    @Relation(
        parentColumn = "homilyFK",
        entityColumn = "homilyFK"
    )
    val themeEntity: BibleHomilyThemeEntity?,

    @Relation(parentColumn = "homilyFK", entityColumn = "homilyID", entity = HomilyEntity::class)
    val homilia: HomilyAll
)

fun BibleHomilyWithAll.asExternalModel() = BibleComment(
    homilia.paterOpusAll.asExternalModel(),
    themeEntity?.reference!!,
    themeEntity.theological!!,
    themeEntity.biblical!!,
    homilia.homilia.fecha,
    homilia.homilia.texto
)

fun BibleHomilyWithAll.asExternalModelNew() = Homily(
    homilia.homilia.texto,
    homilia.homilia.fecha,
    homilia.paterOpusAll.asExternalModel(),
    //themeEntity.reference!!,
    //themeEntity.theological!!,
    themeEntity!!.biblical!!,
)


