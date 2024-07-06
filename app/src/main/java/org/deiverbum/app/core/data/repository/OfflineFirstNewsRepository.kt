package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.deiverbum.app.core.data.Synchronizer
import org.deiverbum.app.core.data.changeListSync
import org.deiverbum.app.core.data.model.asEntity
import org.deiverbum.app.core.data.model.topicCrossReferences
import org.deiverbum.app.core.data.model.topicEntityShells
import org.deiverbum.app.core.database.dao.nia.NewsResourceDao
import org.deiverbum.app.core.database.dao.nia.TopicDao
import org.deiverbum.app.core.database.model.nia.PopulatedNewsResource
import org.deiverbum.app.core.database.model.nia.TopicEntity
import org.deiverbum.app.core.database.model.nia.asExternalModel
import org.deiverbum.app.core.datastore.ChangeListVersions
import org.deiverbum.app.core.datastore.NiaPreferencesDataSource
import org.deiverbum.app.core.model.data.NewsResource
import org.deiverbum.app.core.network.NiaNetworkDataSource
import org.deiverbum.app.core.network.model.NetworkNewsResource
import org.deiverbum.app.core.notifications.Notifier
import javax.inject.Inject

// Heuristic value to optimize for serialization and deserialization cost on client and server
// for each news resource batch.
private const val SYNC_BATCH_SIZE = 40

/**
 * Disk storage backed implementation of the [NewsRepository].
 * Reads are exclusively from local storage to support offline access.
 */
class OfflineFirstNewsRepository @Inject constructor(
    private val niaPreferencesDataSource: NiaPreferencesDataSource,
    private val newsResourceDao: NewsResourceDao,
    private val topicDao: TopicDao,
    private val network: NiaNetworkDataSource,
    private val notifier: Notifier,
) : NewsRepository {

    override fun getNewsResources(
        query: NewsResourceQuery,
    ): Flow<List<NewsResource>> = newsResourceDao.getNewsResources(
        useFilterTopicIds = query.filterTopicIds != null,
        filterTopicIds = query.filterTopicIds ?: emptySet(),
        useFilterNewsIds = query.filterNewsIds != null,
        filterNewsIds = query.filterNewsIds ?: emptySet(),
    )
        .map { it.map(PopulatedNewsResource::asExternalModel) }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        var isFirstSync = false
        return synchronizer.changeListSync(
            versionReader = ChangeListVersions::newsResourceVersion,
            changeListFetcher = { currentVersion ->
                isFirstSync = currentVersion <= 0
                network.getNewsResourceChangeList(after = currentVersion)
            },
            versionUpdater = { latestVersion ->
                copy(newsResourceVersion = latestVersion)
            },
            modelDeleter = newsResourceDao::deleteNewsResources,
            modelUpdater = { changedIds ->
                val userData = niaPreferencesDataSource.userData.first()
                val hasOnboarded = userData.shouldHideOnboarding
                val followedTopicIds = userData.followedTopics

                val existingNewsResourceIdsThatHaveChanged = when {
                    hasOnboarded -> newsResourceDao.getNewsResourceIds(
                        useFilterTopicIds = true,
                        filterTopicIds = followedTopicIds,
                        useFilterNewsIds = true,
                        filterNewsIds = changedIds.toSet(),
                    )
                        .first()
                        .toSet()
                    // No need to retrieve anything if notifications won't be sent
                    else -> emptySet()
                }

                if (isFirstSync) {
                    // When we first retrieve news, mark everything viewed, so that we aren't
                    // overwhelmed with all historical news.
                    niaPreferencesDataSource.setNewsResourcesViewed(changedIds, true)
                }

                // Obtain the news resources which have changed from the network and upsert them locally
                changedIds.chunked(SYNC_BATCH_SIZE).forEach { chunkedIds ->
                    val networkNewsResources = network.getNewsResources(ids = chunkedIds)

                    // Order of invocation matters to satisfy id and foreign key constraints!

                    topicDao.insertOrIgnoreTopics(
                        topicEntities = networkNewsResources
                            .map(NetworkNewsResource::topicEntityShells)
                            .flatten()
                            .distinctBy(TopicEntity::id),
                    )
                    newsResourceDao.upsertNewsResources(
                        newsResourceEntities = networkNewsResources.map(
                            NetworkNewsResource::asEntity,
                        ),
                    )
                    newsResourceDao.insertOrIgnoreTopicCrossRefEntities(
                        newsResourceTopicCrossReferences = networkNewsResources
                            .map(NetworkNewsResource::topicCrossReferences)
                            .distinct()
                            .flatten(),
                    )
                }

                if (hasOnboarded) {
                    val addedNewsResources = newsResourceDao.getNewsResources(
                        useFilterTopicIds = true,
                        filterTopicIds = followedTopicIds,
                        useFilterNewsIds = true,
                        filterNewsIds = changedIds.toSet() - existingNewsResourceIdsThatHaveChanged,
                    )
                        .first()
                        .map(PopulatedNewsResource::asExternalModel)

                    if (addedNewsResources.isNotEmpty()) {
                        notifier.postNewsNotifications(
                            newsResources = addedNewsResources,
                        )
                    }
                }
            },
        )
    }
}