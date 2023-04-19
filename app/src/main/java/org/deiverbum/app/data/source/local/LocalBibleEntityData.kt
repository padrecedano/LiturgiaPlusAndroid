package org.deiverbum.app.data.source.local

import org.deiverbum.app.data.database.dao.TodayDao
import org.deiverbum.app.data.mapper.BibleEntityMapper.toBible
import org.deiverbum.app.data.mapper.BibleMapper.toBibleEntities
import org.deiverbum.app.data.source.BibleEntityData
import org.deiverbum.app.domain.model.Bible
import org.deiverbum.app.domain.model.BibleRequest
import javax.inject.Inject

class LocalBibleEntityData @Inject constructor(
    private val todayDao: TodayDao
) : BibleEntityData {

    override suspend fun getBible(bibleRequest: BibleRequest): List<Bible> {
        val from = 1;
        val to = 5;
        return todayDao.getLiturgy(from, to).toBible()
    }

    override suspend fun addBible(bibles: List<Bible>) {
        //todayDao.insert(prayEntities)
    }
}