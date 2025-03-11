package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHEasterBiblicalEntity
import org.deiverbum.app.core.database.model.entity.LHEasterBiblicalJoinEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.model.data.breviarium.LHAntiphon
import org.deiverbum.app.core.model.data.breviarium.LHOfficeBiblicalEaster
import org.deiverbum.app.core.model.data.breviarium.LHOfficiumPascua
import org.deiverbum.app.core.model.data.breviarium.LHPsalm
import org.deiverbum.app.core.model.data.breviarium.LHPsalmody
import org.deiverbum.app.core.model.liturgia.Liturgy
import org.deiverbum.app.core.model.liturgia.LiturgyTime
import org.deiverbum.app.core.model.liturgia.Oratio
import org.deiverbum.app.core.model.universalis.Universalis
import org.deiverbum.app.util.Constants.PASCUA_TIMEID

/**
 * @author A. Cedano
 * @version 2.0
 * @since 2024.1
 */
data class LocalOfficiumPascua(
    @Embedded
    var parent: LHEasterBiblicalJoinEntity,//LHOfficeBiblicalEasterEntity,

    @Relation(
        entity = LHEasterBiblicalEntity::class,
        parentColumn = "groupID",
        entityColumn = "groupFK"
    )
    var group: List<LHOfficeEasterAll>,

    @Relation(
        entity = UniversalisEntity::class,
        parentColumn = "groupID",
        entityColumn = "oBiblicalFK"
    )
    var universalis: UniversalisEntity,
)

fun LocalOfficiumPascua.asExternalModel(): Universalis {
    val lstLectio: MutableList<LHOfficeBiblicalEaster> = ArrayList()
    val lstPsalmus: MutableList<LHPsalm> = ArrayList()
    val lstAntiphonae: MutableList<LHAntiphon> = ArrayList()
    val lstOratio: MutableList<Oratio> = ArrayList()

    group.forEach {
        lstLectio.add(it.asExternalModel())
        if (it.antiphona != null) {
            lstAntiphonae.add(it.asExternalModelAntiphona())
        }
        if (it.psalmus != null) {
            lstPsalmus.add(it.asExternalModelPsalmus())
        }
        if (it.oratio != null) {
            lstOratio.add(it.asExternalModelOratio())
        }
    }

    return Universalis(
        universalis.todayDate,
        //universalis.timeFK,
        Liturgy(
            1,
            1,
            "Domingo de Pascua de la Resurrección del Señor",
            LiturgyTime(PASCUA_TIMEID, "Tiempo de Pascua", "Tiempo de Pascua"),
            LHOfficiumPascua(
                LHPsalmody(lstPsalmus, lstAntiphonae, 0),
                lstLectio,
                lstOratio,
                "officiumpascua"
            )
        )
    )
}

