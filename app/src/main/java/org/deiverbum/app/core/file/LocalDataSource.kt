package org.deiverbum.app.core.file

import org.deiverbum.app.core.model.data.book.BookTest

/**
 * Interface representing network calls to the NIA backend
 */
interface LocalDataSource {
    suspend fun getTopics(ids: List<String>? = null): List<BookTest>

    //suspend fun getNewsResources(ids: List<String>? = null): List<NetworkNewsResource>

    //suspend fun getTopicChangeList(after: Int? = null): List<NetworkChangeList>

}