package org.deiverbum.app.core.data.model

import org.deiverbum.app.core.database.model.nia.NewsResourceEntity
import org.deiverbum.app.core.database.model.nia.NewsResourceTopicCrossRef
import org.deiverbum.app.core.database.model.nia.TopicEntity
import org.deiverbum.app.core.network.model.NetworkNewsResource
import org.deiverbum.app.core.network.model.NetworkNewsResourceExpanded

fun NetworkNewsResource.asEntity() = NewsResourceEntity(
    id = id,
    title = title,
    content = content,
    url = url,
    headerImageUrl = headerImageUrl,
    publishDate = publishDate,
    type = type,
)

fun NetworkNewsResourceExpanded.asEntity() = NewsResourceEntity(
    id = id,
    title = title,
    content = content,
    url = url,
    headerImageUrl = headerImageUrl,
    publishDate = publishDate,
    type = type,
)

/**
 * A shell [TopicEntity] to fulfill the foreign key constraint when inserting
 * a [NewsResourceEntity] into the DB
 */
fun NetworkNewsResource.topicEntityShells() =
    topics.map { topicId ->
        TopicEntity(
            id = topicId,
            name = "",
            url = "",
            imageUrl = "",
            shortDescription = "",
            longDescription = "",
        )
    }

fun NetworkNewsResource.topicCrossReferences(): List<NewsResourceTopicCrossRef> =
    topics.map { topicId ->
        NewsResourceTopicCrossRef(
            newsResourceId = id,
            topicId = topicId,
        )
    }
