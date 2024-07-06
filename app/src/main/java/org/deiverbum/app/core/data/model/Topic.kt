package org.deiverbum.app.core.data.model

import org.deiverbum.app.core.database.model.nia.TopicEntity
import org.deiverbum.app.core.network.model.NetworkTopic

fun NetworkTopic.asEntity() = TopicEntity(
    id = id,
    name = name,
    shortDescription = shortDescription,
    longDescription = longDescription,
    url = url,
    imageUrl = imageUrl,
)
