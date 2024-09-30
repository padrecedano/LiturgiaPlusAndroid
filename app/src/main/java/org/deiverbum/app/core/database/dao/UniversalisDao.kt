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

package org.deiverbum.app.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.core.database.model.UITopicEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.external.CommentariiExternal
import org.deiverbum.app.core.database.model.external.CompletoriumExternal
import org.deiverbum.app.core.database.model.external.HomiliaeExternal
import org.deiverbum.app.core.database.model.external.LaudesExternal
import org.deiverbum.app.core.database.model.external.MissaeLectionumExternal
import org.deiverbum.app.core.database.model.external.MixtusExternal
import org.deiverbum.app.core.database.model.external.NonamExternal
import org.deiverbum.app.core.database.model.external.OfficiumExternal
import org.deiverbum.app.core.database.model.external.PopulatedVesperasResource
import org.deiverbum.app.core.database.model.external.SanctiiExternal
import org.deiverbum.app.core.database.model.external.SextamExternal
import org.deiverbum.app.core.database.model.external.TertiamExternal
import org.deiverbum.app.core.database.model.external.UniversalisExternal
import org.deiverbum.app.core.database.model.nia.NewsResourceEntity
import org.deiverbum.app.core.model.data.Universalis

/**
 * DAO for [NewsResource] and [NewsResourceEntity] access
 */
@Dao
interface UniversalisDao {
    companion object {
        const val universalisByDate = "SELECT * FROM universalis WHERE todayDate IN(:filterDates)"
    }

    @Query(value = "SELECT * FROM universalis WHERE todayDate=20240719")
    fun getUniversalisList(): Flow<List<UniversalisEntity>>

    @Query(value = "SELECT * FROM ui_topic")
    fun getTopicEntities(): Flow<List<UITopicEntity>>

    @Query(
        value = """
        SELECT * FROM ui_topic
        WHERE id = :id
    """,
    )
    fun getTopicEntityy(id: Int): Flow<UITopicEntity>

    /**
     * Fetches news resources that match the query parameters
     */
    @Transaction
    @Query(universalisByDate)
    fun getUniversalisByDate(
        filterDates: Set<Int> = emptySet(),
    ): Flow<List<UniversalisExternal>>

    @Transaction
    @Query(universalisByDate)
    fun getUniversalisOfToday(
        filterDates: Set<Int> = emptySet(),
    ): Flow<List<UniversalisExternal>>

    /**
     * Fetches news resources that match the query parameters
     */

    @Transaction
    @Query(universalisByDate)
    fun getMixtusByDate(
        filterDates: Set<Int> = emptySet(),
    ): Flow<List<MixtusExternal>>

    @Transaction
    @Query(universalisByDate)
    fun getOfficiumByDate(
        filterDates: Set<Int> = emptySet(),
    ): Flow<List<OfficiumExternal>>

    @Transaction
    @Query(universalisByDate)
    fun getLaudesByDate(
        filterDates: Set<Int> = emptySet(),
    ): Flow<List<LaudesExternal>>

    @Transaction
    @Query(universalisByDate)
    fun getTertiamByDate(
        filterDates: Set<Int> = emptySet(),
    ): Flow<List<TertiamExternal>>

    @Transaction
    @Query(universalisByDate)
    fun getSextamByDate(
        filterDates: Set<Int> = emptySet(),
    ): Flow<List<SextamExternal>>

    @Transaction
    @Query(universalisByDate)
    fun getNonamByDate(
        filterDates: Set<Int> = emptySet(),
    ): Flow<List<NonamExternal>>

    @Transaction
    @Query(universalisByDate)
    fun getVesperasByDate(filterDates: Set<Int> = emptySet()): Flow<List<PopulatedVesperasResource>>

    @Transaction
    @Query(universalisByDate)
    fun getCompletoriumByDate(filterDates: Set<Int> = emptySet()): Flow<List<CompletoriumExternal>>

    @Transaction
    @Query(universalisByDate)
    fun getMissaeLectionumByDate(filterDates: Set<Int> = emptySet()): Flow<List<MissaeLectionumExternal>>

    @Transaction
    @Query(universalisByDate)
    fun getCommentariiByDate(filterDates: Set<Int> = emptySet()): Flow<List<CommentariiExternal>>

    @Transaction
    @Query("SELECT * FROM saint WHERE theMonth=:theMonth AND theDay=:theDay")
    fun getSanctiiByDate(theMonth: Int?, theDay: Int?): Flow<List<SanctiiExternal>>


    @Transaction
    @Query(universalisByDate)
    fun getHomiliaeByDate(filterDates: Set<Int> = emptySet()): Flow<List<HomiliaeExternal>>


    /**
     * Fetches news resources that match the query parameters
     */
    @Transaction
    @Query(
        value = """
            SELECT * FROM universalis
            WHERE 
                todayDate =:todayDate
    """,
    )
    fun getUniversalisPopulatedByDate(
        todayDate: Int = 0,
    ): Flow<SextamExternal>

    @Transaction
    @Query(
        value = """
            SELECT * FROM universalis
            WHERE 
                todayDate IN(:todayDate)
    """,
    )
    fun getTopicEntity(todayDate: Int): Flow<UniversalisEntity>

    @Transaction
    @Query(
        value = """
            SELECT * FROM universalis
            WHERE 
                todayDate IN(:todayDate)
    """,
    )
    fun getUniversalisByDate(todayDate: Int): Flow<UniversalisEntity>


    /**
     * Inserts [topicEntities] into the db if they don't exist, and ignores those that do
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreUniversalis(topicEntities: List<UniversalisEntity>): List<Long>

    /**
     * Inserts or updates [entities] in the db under the specified primary keys
     */
    @Upsert
    suspend fun upsertUniversalis(entities: List<UniversalisEntity>)

    @Upsert(entity = UniversalisEntity::class)
    suspend fun upsertUniversaliss(entities: List<Universalis>)

    /**
     * Deletes rows in the db matching the specified [ids]
     */
    @Query(
        value = """
            DELETE FROM universalis
            WHERE todayDate in (:ids)
        """,
    )
    suspend fun deleteUniversalis(ids: List<String>)
}
