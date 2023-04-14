package org.deiverbum.app.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.data.entity.BibleReadingEntity
import org.deiverbum.app.data.entity.LHOfficeBiblicalEasterEntity
import org.deiverbum.app.data.entity.PrayerEntity
import org.deiverbum.app.model.LHOfficeBiblicalEaster

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class LHOfficeEasterAll {
    @JvmField
    @Embedded
    var biblical: LHOfficeBiblicalEasterEntity? = null

    @JvmField
    @Relation(
        parentColumn = "readingFK",
        entityColumn = "readingID",
        entity = BibleReadingEntity::class
    )
    var reading: BibleReadingWithBook? = null

    @JvmField
    @Relation(parentColumn = "prayerFK", entityColumn = "prayerID", entity = PrayerEntity::class)
    var prayer: PrayerEntity? = null
    val domainModel: LHOfficeBiblicalEaster
        get() {
            val dm = LHOfficeBiblicalEaster()
            dm.setOrden(biblical!!.theOrder)
            dm.theme = biblical!!.theme
            dm.libro = reading!!.libro!!.domainModel
            dm.verseChapter = reading!!.lectura!!.capitulo.toString()
            dm.verseFrom = reading!!.lectura!!.desde.toString()
            dm.verseTo = reading!!.lectura!!.hasta.toString()
            dm.setCita(reading!!.lectura!!.cita)
            dm.text = reading!!.lectura!!.texto
            dm.prayer = prayer!!.domainModel
            return dm
        }
}