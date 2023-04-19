package org.deiverbum.app.data.source.local

import android.text.SpannableStringBuilder
import org.deiverbum.app.data.database.dao.TodayDao
import org.deiverbum.app.data.source.TodayEntityData
import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.model.Today
import javax.inject.Inject

class LocalTodayEntityData @Inject constructor(
    private val todayDao: TodayDao
) : TodayEntityData {

    override suspend fun getToday(todayRequest: TodayRequest): SpannableStringBuilder {
        val allToday: Today? = todayDao.getMixtoOfToday(20230419)?.domainModelToday;//toMixtoDomainModel()
        val sb=allToday?.getAllForView(false,false)
        return allToday?.getAllForView(false,false)!!
    }

    override suspend fun addToday(today: SpannableStringBuilder) {
    }
}