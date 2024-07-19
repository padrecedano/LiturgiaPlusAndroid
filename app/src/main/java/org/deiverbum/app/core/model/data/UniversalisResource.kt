package org.deiverbum.app.core.model.data

/**
 * External data layer representation of a fully populated NiA news resource
 */
data class UniversalisResource(
    val todayDate: Int,
    val content: String,

    /*val title: String,
    val url: String,
    val headerImageUrl: String?,
    val publishDate: Instant,
    val type: String,*/
    val topics: List<Universalis>,
)
