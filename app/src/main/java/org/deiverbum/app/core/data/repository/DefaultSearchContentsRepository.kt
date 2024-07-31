/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.deiverbum.app.core.data.repository


import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.core.database.dao.nia.NewsResourceDao
import org.deiverbum.app.core.database.dao.nia.NewsResourceFtsDao
import org.deiverbum.app.core.database.dao.nia.TopicDao
import org.deiverbum.app.core.database.dao.nia.TopicFtsDao
import org.deiverbum.app.core.model.data.SearchResult
import org.deiverbum.app.core.network.Dispatcher
import org.deiverbum.app.core.network.NiaDispatchers
import javax.inject.Inject

class DefaultSearchContentsRepository @Inject constructor(
    private val newsResourceDao: NewsResourceDao,
    private val newsResourceFtsDao: NewsResourceFtsDao,
    private val topicDao: TopicDao,
    private val topicFtsDao: TopicFtsDao,
    @Dispatcher(NiaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : SearchContentsRepository {
    override suspend fun populateFtsData() {
        TODO("Not yet implemented")
    }

    override fun searchContents(searchQuery: String): Flow<SearchResult> {
        TODO("Not yet implemented")
    }

    override fun getSearchContentsCount(): Flow<Int> {
        TODO("Not yet implemented")
    }


}
