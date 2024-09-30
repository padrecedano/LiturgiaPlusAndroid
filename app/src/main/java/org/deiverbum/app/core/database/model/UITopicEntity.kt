package org.deiverbum.app.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import org.deiverbum.app.core.model.data.UITopic

@Entity(
    tableName = "ui_topic",
    foreignKeys =
    [
        ForeignKey(
            entity = UIGroupEntity::class,
            parentColumns = arrayOf("groupID"),
            childColumns = arrayOf("groupFK"),
            onDelete = CASCADE,
            onUpdate = CASCADE
        )]
)
data class UITopicEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    @ColumnInfo(name = "groupFK", index = true)
    val groupFK: Int,
    val priority: Int,
)

fun UITopicEntity.asExternalModel() = UITopic(
    id = id,
    name = name,
    description = description,
    groupFK = groupFK,
    priority = priority,
)
