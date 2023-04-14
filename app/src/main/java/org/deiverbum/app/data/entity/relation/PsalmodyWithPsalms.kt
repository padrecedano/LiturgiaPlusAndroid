package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.*

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class PsalmodyWithPsalms {
    @JvmField
    @Embedded
    var salmodia: LHPsalmodyEntity? = null

    @JvmField
    @Relation(
        parentColumn = "groupFK",
        entityColumn = "groupID",
        entity = LHPsalmodyJoinEntity::class
    )
    var salmodiaEntity: LHPsalmodyJoinEntity? = null

    @JvmField
    @Relation(parentColumn = "readingFK", entityColumn = "psalmID", entity = PsalmEntity::class)
    var psalmEntity: PsalmEntity? = null

    @JvmField
    @Relation(
        parentColumn = "antiphonFK",
        entityColumn = "antiphonID",
        entity = LHAntiphonEntity::class
    )
    var antiphonEntity: LHAntiphonEntity? = null

    @JvmField
    @Relation(parentColumn = "themeFK", entityColumn = "themeID", entity = LHThemeEntity::class)
    var tema: LHThemeEntity? = null

    @JvmField
    @Relation(
        parentColumn = "epigraphFK",
        entityColumn = "epigraphID",
        entity = EpigraphEntity::class
    )
    var epigrafe: EpigraphEntity? = null
    fun getEpigrafe(): String {
        return if (epigrafe != null) epigrafe!!.epigrafe else ""
    }

    val salmoText: String
        get() = if (psalmEntity != null) psalmEntity!!.salmo else ""
    val ref: String
        get() = if (psalmEntity != null) psalmEntity!!.getSalmoRef() else ""
    val antifona: String
        get() = if (antiphonEntity != null) antiphonEntity!!.antifona else ""

    fun getTema(): String {
        return if (tema != null) tema!!.tema else ""
    }

    val parte: String
        get() = salmodia!!.getParte()
    val orden: String
        get() = if (salmodia != null) salmodia!!.orden.toString() else "0"
}