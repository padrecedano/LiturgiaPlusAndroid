package org.deiverbum.app.data.mapper

import org.deiverbum.app.data.entity.HomilyEntity
import org.deiverbum.app.data.entity.TodayEntity
import org.deiverbum.app.data.entity.relation.TodayMixto
import org.deiverbum.app.model.Mixto
import org.deiverbum.app.model.Today

object TodayEntityMapper {

    fun List<TodayEntity>.toTodayDomainModel(): List<Today> {
        val allToday = ArrayList<Today>()
        forEach {
            var t  = Today();
            t.liturgyFK=it.liturgyFK
            allToday.add(t)
        }
        return allToday
    }

    fun TodayMixto.toMixtoDomainModel(): Today {
        val dm = Mixto()
        dm.liturgyID=1
        return domainModelToday //domainModelToday
    }
}