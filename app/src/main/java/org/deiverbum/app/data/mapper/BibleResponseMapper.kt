package org.deiverbum.app.data.mapper

import org.deiverbum.app.data.model.BibleResponse
import org.deiverbum.app.domain.model.Bible

object BibleResponseMapper {

    fun BibleResponse.toBible(): List<Bible> {
        val bibles = ArrayList<Bible>()
        return bibles
    }
}