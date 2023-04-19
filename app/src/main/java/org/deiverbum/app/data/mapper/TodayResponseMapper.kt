package org.deiverbum.app.data.mapper

import org.deiverbum.app.data.model.TodayResponse
import org.deiverbum.app.model.Today

object TodayResponseMapper {

    fun TodayResponse.toHomily(): List<Today> {
        val allToday = ArrayList<Today>()
        return allToday
    }
}