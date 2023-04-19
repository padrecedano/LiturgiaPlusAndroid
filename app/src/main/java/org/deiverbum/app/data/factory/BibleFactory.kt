package org.deiverbum.app.data.factory

import org.deiverbum.app.data.source.BibleEntityData
import org.deiverbum.app.data.source.local.LocalBibleEntityData
import org.deiverbum.app.data.source.network.NetworkBibleEntityData
import org.deiverbum.app.util.Source
import javax.inject.Inject

class BibleFactory @Inject constructor(
    private val networkBibleEntityData: NetworkBibleEntityData,
    private val localBibleEntityData: LocalBibleEntityData
) {

    fun create(source: Source): BibleEntityData {
        return when (source) {
            Source.NETWORK -> networkBibleEntityData
            else -> localBibleEntityData
        }
    }
}