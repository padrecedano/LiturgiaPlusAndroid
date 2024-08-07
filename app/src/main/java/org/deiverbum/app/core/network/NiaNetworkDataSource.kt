package org.deiverbum.app.core.network

import org.deiverbum.app.core.network.model.NetworkChangeList
import org.deiverbum.app.core.network.model.NetworkNewsResource
import org.deiverbum.app.core.network.model.NetworkTopic
import org.deiverbum.app.core.network.model.NetworkUniversalisResource

/**
 * Interface representing network calls to the NIA backend
 */
interface NiaNetworkDataSource {
    suspend fun getTopics(ids: List<String>? = null): List<NetworkTopic>
    suspend fun getUniversalis(ids: List<Int> = emptyList()): NetworkUniversalisResource

    suspend fun getNewsResources(ids: List<String>? = null): List<NetworkNewsResource>

    suspend fun getTopicChangeList(after: Int? = null): List<NetworkChangeList>

    suspend fun getNewsResourceChangeList(after: Int? = null): List<NetworkChangeList>
}
