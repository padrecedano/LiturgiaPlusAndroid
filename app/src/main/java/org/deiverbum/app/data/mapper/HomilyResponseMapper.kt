package org.deiverbum.app.data.mapper

import org.deiverbum.app.data.model.HomilyResponse
import org.deiverbum.app.domain.model.Homily

object HomilyResponseMapper {

    fun HomilyResponse.toHomily(): List<Homily> {
        val allHomily = ArrayList<Homily>()
        return allHomily
    }
}