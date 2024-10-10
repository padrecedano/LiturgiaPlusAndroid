package org.deiverbum.app.core.database.model.external

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.MassReadingEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.database.model.relation.LiturgyTimeAssoc
import org.deiverbum.app.core.database.model.relation.MassReadingWithComments
import org.deiverbum.app.core.database.model.relation.asExternalModel
import org.deiverbum.app.core.database.model.relation.asExternalModelNew
import org.deiverbum.app.core.model.data.BibleComment
import org.deiverbum.app.core.model.data.BibleCommentList
import org.deiverbum.app.core.model.data.BiblicaWithComments
import org.deiverbum.app.core.model.data.Commentarii
import org.deiverbum.app.core.model.data.Liturgy
import org.deiverbum.app.core.model.data.Missae
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.model.data.UniversalisResource

/**
 * Representación de los Comentarios Bíblicos para la capa de datos externa.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 * @see Missae
 */

data class CommentariiExternal(
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgia: LiturgyTimeAssoc,

    @Relation(
        entity = MassReadingEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyFK"
    )
    var comments: List<MassReadingWithComments>
)

fun CommentariiExternal.asExternalModel(): UniversalisResource {
    val emList = BibleCommentList()
    val allComentarios: MutableList<List<BibleComment>> = ArrayList()
    for (item in comments) {
        allComentarios += item.asExternalModel()
    }
    emList.commentarii = allComentarios.toMutableList()
    val biblicaWithComments: MutableList<BiblicaWithComments> = ArrayList()

    for (item in comments) {
        biblicaWithComments.add(item.asExternalModelNew())
    }

    val typus = Commentarii(
        biblicaWithComments,
        "commentarii"
    )
    return UniversalisResource(
        data = listOf(
            Universalis(
                universalis.todayDate,
                Liturgy(
                    liturgia.parent.semana,
                    liturgia.parent.dia,
                    liturgia.parent.nombre,
                    liturgia.entity.asExternalModel(),
                    typus
                )
            )
        )
    )
}
