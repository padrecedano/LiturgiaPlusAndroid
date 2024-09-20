package org.deiverbum.app.core.model.data.ui

import kotlinx.serialization.Serializable

@Serializable
data class ItemUI(
    val id: Int,
    val title: String,
    val group: Int,
    val nav: String
)
