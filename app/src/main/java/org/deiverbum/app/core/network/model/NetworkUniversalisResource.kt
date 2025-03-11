package org.deiverbum.app.core.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.model.universalis.Universalis
import org.deiverbum.app.core.model.universalis.asEntity

/**
 * Network representation of [NewsResource] when fetched from /newsresources
 */
//@Serializable
@JsonClass(generateAdapter = true)
data class NetworkUniversalisResource(
    @Json(name = "lastUpdate") val lastUpdate: String,
    @Json(name = "data") val data: List<Universalis> = listOf(),
)

fun NetworkUniversalisResource.asEntity(): ArrayList<UniversalisEntity> {
    var newData = ArrayList<UniversalisEntity>()
    data.forEach { newData.add(it.asEntity()) }
    return newData

}


/**
 * Network representation of [NewsResource] when fetched from /newsresources/{id}
 */


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
