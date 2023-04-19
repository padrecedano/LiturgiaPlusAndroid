package org.deiverbum.app.data.mapper

import org.deiverbum.app.domain.model.Bible
import org.deiverbum.app.model.Liturgy

object BibleMapper {

    fun List<Bible>.toBibleEntities(): List<Liturgy> {
        val allBible = ArrayList<Liturgy>()

        forEach {
            //prayEntities.add(PrayEntity(it.liturgyID, it.timeFK, it.name))
        }
        return allBible
    }
}