package org.deiverbum.app.data.factory

import org.deiverbum.app.data.source.BiblicalCommentEntityData
import org.deiverbum.app.data.source.HomilyEntityData
import org.deiverbum.app.data.source.local.LocalBiblicalCommentEntityData
import org.deiverbum.app.data.source.local.LocalHomilyEntityData
import org.deiverbum.app.data.source.network.NetworkBiblicalCommentEntityData
import org.deiverbum.app.data.source.network.NetworkHomilyEntityData
import org.deiverbum.app.util.Source
import javax.inject.Inject

class BiblicalCommentFactory @Inject constructor(
    private val networkBiblicalCommentEntityData: NetworkBiblicalCommentEntityData,
    private val localBiblicalCommentEntityData: LocalBiblicalCommentEntityData
) {

    fun create(source: Source): BiblicalCommentEntityData {
        return when (source) {
            Source.NETWORK -> networkBiblicalCommentEntityData
            else -> localBiblicalCommentEntityData
        }
    }
}