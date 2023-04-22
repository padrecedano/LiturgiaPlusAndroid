package org.deiverbum.app.data.source.local

import android.text.SpannableStringBuilder
import android.util.Log
import org.deiverbum.app.data.database.dao.TodayDao
import org.deiverbum.app.data.mapper.HomilyEntityMapper.toHomilyDomainModel
import org.deiverbum.app.data.source.FileEntityData
import org.deiverbum.app.data.source.HomilyEntityData
import org.deiverbum.app.data.wrappers.CustomException
import org.deiverbum.app.data.wrappers.DataWrapper
import org.deiverbum.app.domain.model.FileRequest
import org.deiverbum.app.domain.model.Homily
import org.deiverbum.app.domain.model.HomilyRequest
import org.deiverbum.app.model.Rosario
import org.deiverbum.app.util.AssetProvider
import org.deiverbum.app.util.FileReaderUtil
import javax.inject.Inject

class LocalFileEntityData @Inject constructor(
    //private val todayDao: FileData
    //private val todayDaoo: FileData
    private val assetProvider: AssetProvider
) : FileEntityData {

    override suspend fun getFile(fileRequest: FileRequest): String {
        //val allHomily: List<Homily> = todayDao.getHomily().toHomilyDomainModel()
        val data = assetProvider.getRosary(1)
        data?.error?.message?.let { Log.d("DDDk", it) }
        //todayDaoo.getRosario(1)
        data?.data?.avemaria?.let { Log.d("DDD", it) }
        return data?.data?.getForView(isBrevis = true, nightMode = false).toString()
    }

    override suspend fun addFile(homily: String) {
        //
    }
}