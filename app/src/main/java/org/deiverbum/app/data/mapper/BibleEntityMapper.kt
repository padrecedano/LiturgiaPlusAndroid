package org.deiverbum.app.data.mapper

import org.deiverbum.app.data.entity.LiturgyEntity
import org.deiverbum.app.domain.model.Bible

object BibleEntityMapper {

    fun List<LiturgyEntity>.toBible(): List<Bible> {
        val bibles = ArrayList<Bible>()
        forEach {
            bibles.add(Bible(it.liturgiaId,it.nombre, it.tiempoFK))
        }

        return bibles
    }
}