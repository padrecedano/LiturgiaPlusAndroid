package org.deiverbum.app.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "ui_group",
)
data class UIGroupEntity(
    @PrimaryKey
    val groupID: Int,
    val name: String,
    val description: String
)