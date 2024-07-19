package org.deiverbum.app.core.network.model

import com.squareup.moshi.JsonClass
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import org.deiverbum.app.core.model.data.Universalis

/**
 * Network representation of [NewsResource] when fetched from /newsresources
 */
//@Serializable
@JsonClass(generateAdapter = true)
data class NetworkUniversalisResource(
    val lastUpdate: String,
    val data: List<Universalis> = listOf(),
)

/**
 * Network representation of [NewsResource] when fetched from /newsresources/{id}
 */
@Serializable
data class NetworkUniversalisResourceExpanded(
    val id: String,
    val title: String,
    val content: String,
    val url: String,
    val headerImageUrl: String,
    val publishDate: Instant,
    val type: String,
    val topics: List<NetworkTopic> = listOf(),
)
