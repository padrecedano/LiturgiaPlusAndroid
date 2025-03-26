package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.EpigraphEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmJoinEntity
import org.deiverbum.app.core.database.model.entity.LHThemeEntity
import org.deiverbum.app.core.model.breviarium.LHPsalm

/**
 * Obtiene todos los componentes del salmo, asociando [LHPsalmJoinEntity] con sus entidades relacionadas
 * [LHPsalmEntity], [LHThemeEntity] y [EpigraphEntity]
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */
data class LHPsalmWithAll(
    @Embedded
    var psalmodyEntity: LHPsalmJoinEntity,

    @Relation(parentColumn = "readingFK", entityColumn = "psalmID", entity = LHPsalmEntity::class)
    var psalmEntity: LHPsalmEntity,

    @Relation(parentColumn = "themeFK", entityColumn = "themeID", entity = LHThemeEntity::class)
    var themeEntity: LHThemeEntity? = null,

    @Relation(
        parentColumn = "epigraphFK",
        entityColumn = "epigraphID",
        entity = EpigraphEntity::class
    )
    var epigraphEntity: EpigraphEntity? = null
) {
    val epigraph: String
        get() = if (epigraphEntity != null && epigraphEntity!!.epigrafe.isNotEmpty()) epigraphEntity!!.epigrafe else ""

    val psalm: String
        get() = psalmEntity.salmo

    val theQuote: String
        get() = if (!psalmEntity.salmoRef.isNullOrEmpty()) psalmEntity.salmoRef!! else ""

    val theme: String
        get() =
            if (!themeEntity?.tema.isNullOrEmpty()) themeEntity?.tema!! else ""

    val thePart: Int?
        get() =
            if (psalmodyEntity.thePart != null) psalmodyEntity.thePart else 0

    val theOrder: Int
        get() = psalmodyEntity.theOrder
}

fun LHPsalmWithAll.asExternalModel() = LHPsalm(
    theOrder, true,
    theQuote,
    theme,
    epigraph,
    thePart!!,
    psalm
)