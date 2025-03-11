package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.MassReadingEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.traditio.BibleComment
import org.deiverbum.app.core.model.data.traditio.BibleCommentList
import org.deiverbum.app.core.model.data.traditio.BiblicaWithComments
import org.deiverbum.app.core.model.data.traditio.Commentarii
import org.deiverbum.app.core.model.data.traditio.Homily
import org.deiverbum.app.core.model.liturgia.Liturgy
import org.deiverbum.app.core.model.universalis.Universalis

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class CommentariiLocal(
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

fun CommentariiLocal.asExternalModel(): Universalis {
    val extModel = universalis.asExternalModel()
    val emList = BibleCommentList()
    val allComentarios: MutableList<List<BibleComment>> = ArrayList()
    val homiliae: MutableList<Homily> = ArrayList()

    for (item in comments) {
        allComentarios += item.asExternalModel()
    }
    emList.commentarii = allComentarios.toMutableList()
    //val traditio = Traditio.Comment(emList, "comment")
    //extModel.liturgyTime = liturgia.liturgyTime.asExternalModel()
    //val extLiturgyDay = Liturgy(traditio, liturgia.liturgiaEntity.nombre, 11)
    //extModel.liturgia = extLiturgyDay
    val biblicaWithComments: MutableList<BiblicaWithComments> = ArrayList()
    //val biblicaWithCommentss=BiblicaWithComments(MissaeLectionum(),allComentarios)
    var i = 0
    comments.forEachIndexed { index, item ->
        if (index == 0) {
            val theModel = item.lecturaOne.asExternalModelMissae(40, "")
            //theModel.biblica = lecturaOne.asExternalModelMissae(misaLectura.orden, misaLectura.tema)
            //biblicaWithComments[i].biblica=theModel

        }
        //homiliae.add(item.asExternalModelNew())
        println("index = $index, xrt = ${item.lecturaOne.lectura.cita} ")

    }

    for (item in comments) {


        biblicaWithComments.add(item.asExternalModelNew())
    }

    val typus = Commentarii(
        biblicaWithComments,
        "laudes"
    )
    return Universalis(
        universalis.todayDate,
        Liturgy(
            liturgia.parent.semana,
            liturgia.parent.dia,
            liturgia.parent.nombre,
            liturgia.entity.asExternalModel(),
            typus
        )
    )
    //return extModel
}