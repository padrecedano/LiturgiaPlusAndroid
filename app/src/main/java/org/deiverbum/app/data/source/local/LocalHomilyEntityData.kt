package org.deiverbum.app.data.source.local

import android.text.SpannableStringBuilder
import org.deiverbum.app.data.database.dao.TodayDao
import org.deiverbum.app.data.mapper.HomilyEntityMapper.toHomilyDomainModel
import org.deiverbum.app.data.source.HomilyEntityData
import org.deiverbum.app.domain.model.Homily
import org.deiverbum.app.domain.model.HomilyRequest
import javax.inject.Inject

class LocalHomilyEntityData @Inject constructor(
    private val todayDao: TodayDao
) : HomilyEntityData {

    override suspend fun getHomily(homilyRequest: HomilyRequest): SpannableStringBuilder {
        val allHomily: List<Homily> = todayDao.getHomily().toHomilyDomainModel()
        val sb = SpannableStringBuilder()
        allHomily.forEach {
            sb.append("\n\n")
            sb.append(it.homilyID.toString())
            sb.append("\n\n")
            sb.append(it.homily)
        }
        return sb;
    }

    override suspend fun addHomily(homily: SpannableStringBuilder) {
        //
    }
}