package org.deiverbum.app.core.model.data

data class UITopic(
    val id: Int,
    val name: String,
    val description: String,
    val groupFK: Int,
    val priority: Int,
)
