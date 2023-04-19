package org.deiverbum.app.data.factory

import org.deiverbum.app.data.source.HomilyEntityData
import org.deiverbum.app.data.source.TodayEntityData
import org.deiverbum.app.data.source.local.LocalHomilyEntityData
import org.deiverbum.app.data.source.local.LocalTodayEntityData
import org.deiverbum.app.data.source.network.NetworkHomilyEntityData
import org.deiverbum.app.data.source.network.NetworkTodayEntityData
import org.deiverbum.app.util.Source
import javax.inject.Inject

class TodayFactory @Inject constructor(
    private val networkTodayEntityData: NetworkTodayEntityData,
    private val localTodayEntityData: LocalTodayEntityData
) {

    fun create(source: Source): TodayEntityData {
        return when (source) {
            Source.NETWORK -> networkTodayEntityData
            else -> localTodayEntityData
        }
    }
}