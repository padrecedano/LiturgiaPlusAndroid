package org.deiverbum.app.data.mapper

import org.deiverbum.app.data.entity.HomilyEntity
import org.deiverbum.app.domain.model.Homily

object HomilyEntityMapper {

    fun List<HomilyEntity>.toHomilyDomainModel(): List<Homily> {
        val allHomily = ArrayList<Homily>()
        forEach {
            allHomily.add(Homily(it.homiliaId,it.texto))
        }
        return allHomily
    }
}