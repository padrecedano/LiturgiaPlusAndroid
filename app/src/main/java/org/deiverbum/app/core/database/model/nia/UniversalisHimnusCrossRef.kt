/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.deiverbum.app.core.database.model.nia

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import org.deiverbum.app.core.database.model.entity.LHHymnEntity

/**
 * Cross reference for many to many relationship between [NewsResourceEntity] and [TopicEntity]
 */
@Entity(
    tableName = "lh_hymn_join",
    //primaryKeys = ["groupID", "hymnFK"],
    foreignKeys = [
        ForeignKey(
            entity = LHHymnEntity::class,
            parentColumns = ["hymnID"],
            childColumns = ["hymnFK"],
            onDelete = ForeignKey.CASCADE,
        ),
        /*ForeignKey(
            entity = TopicEntity::class,
            parentColumns = ["id"],
            childColumns = ["topic_id"],
            onDelete = ForeignKey.CASCADE,
        ),*/
    ],
    /*indices = [
        Index(value = ["groupID"]),
        Index(value = ["hymnFK"]),
    ],*/
)
data class UniversalisHimnusCrossRef(
    @ColumnInfo(name = "hymnID")
    val newsResourceId: Int,
    @ColumnInfo(name = "hymnFK")
    val topicId: Int,
)
