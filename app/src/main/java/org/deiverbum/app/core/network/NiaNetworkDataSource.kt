package org.deiverbum.app.core.network

import org.deiverbum.app.core.model.data.Universalis

interface NiaNetworkDataSource {
    suspend fun getNewsResourceChangeList(after: Int? = null): Universalis

}