package org.deiverbum.app.data.factory

import org.deiverbum.app.data.source.HomilyEntityData
import org.deiverbum.app.data.source.local.LocalHomilyEntityData
import org.deiverbum.app.data.source.network.NetworkHomilyEntityData
import org.deiverbum.app.util.Source
import javax.inject.Inject

class HomilyFactory @Inject constructor(
    private val networkHomilyEntityData: NetworkHomilyEntityData,
    private val localHomilyEntityData: LocalHomilyEntityData
) {

    fun create(source: Source): HomilyEntityData {
        return when (source) {
            Source.NETWORK -> networkHomilyEntityData
            else -> localHomilyEntityData
        }
    }
}