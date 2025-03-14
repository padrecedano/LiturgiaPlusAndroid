package org.deiverbum.app.core.database.model.external

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.LiturgyHomilyJoinEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.database.model.relation.LiturgyTimeAssoc
import org.deiverbum.app.core.database.model.relation.LiturgyWithHomilies
import org.deiverbum.app.core.database.model.relation.asExternalModel
import org.deiverbum.app.core.model.data.missae.Missae
import org.deiverbum.app.core.model.data.traditio.Homily
import org.deiverbum.app.core.model.liturgia.Liturgy
import org.deiverbum.app.core.model.universalis.Universalis

/**
 * Representación de las Homilías para la capa de datos externa.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 * @see Missae
 * @see Homiliae
 */

data class HomiliaeExternal(
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgia: LiturgyTimeAssoc,

    @Relation(
        entity = LiturgyHomilyJoinEntity::class,
        parentColumn = "liturgyFK",
        entityColumn = "liturgyFK"
    )
    var homilyes: List<LiturgyWithHomilies>
)

fun HomiliaeExternal.asExternalModel(): Universalis {
    val homilyList: MutableList<Homily> = ArrayList()

    for (item in homilyes) {
        homilyList.add(item.asExternalModel())
    }

    return Universalis(
        universalis.todayDate,
        Liturgy(
            liturgia.parent.semana,
            liturgia.parent.dia,
            liturgia.parent.nombre,
            liturgia.entity.asExternalModel(),
            Missae(
                false,
                universalis.timeFK,
                "missae",
                homilyList
            )
        )
    )

}