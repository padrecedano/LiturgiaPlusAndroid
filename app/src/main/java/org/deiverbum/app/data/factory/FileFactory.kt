package org.deiverbum.app.data.factory

import org.deiverbum.app.data.source.FileEntityData
import org.deiverbum.app.data.source.HomilyEntityData
import org.deiverbum.app.data.source.local.LocalFileEntityData
import org.deiverbum.app.data.source.local.LocalHomilyEntityData
import org.deiverbum.app.data.source.network.NetworkHomilyEntityData
import org.deiverbum.app.util.Source
import javax.inject.Inject

class FileFactory @Inject constructor(
    private val networkHomilyEntityData: NetworkHomilyEntityData,
    private val localFileEntityData: LocalFileEntityData
) {

    fun create(source: Source): FileEntityData {
        return when (source) {
            Source.LOCAL -> localFileEntityData
            else -> localFileEntityData
        }
    }
}