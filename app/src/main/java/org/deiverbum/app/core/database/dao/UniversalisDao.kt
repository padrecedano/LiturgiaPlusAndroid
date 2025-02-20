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
import org.deiverbum.app.core.database.model.external.RosariumExternal
import org.deiverbum.app.core.database.model.external.SanctiiExternal
import org.deiverbum.app.core.database.model.external.SextamExternal
import org.deiverbum.app.core.database.model.external.TertiamExternal
import org.deiverbum.app.core.database.model.external.UniversalisExternal
import org.deiverbum.app.core.model.data.Universalis

/**
 * DAO para acceder a [UniversalisEntity]
 */
@Dao
interface UniversalisDao {
    companion object {
        const val universalisByDate = "SELECT * FROM universalis WHERE todayDate IN(:filterDates)"
        const val universalisByDateNew = "SELECT * FROM universalis WHERE todayDate =:filterDate"

    }

    @Query(value = "SELECT * FROM universalis WHERE todayDate=20240719")
    fun getUniversalisList(): Flow<List<UniversalisEntity>>

    @Query(value = "SELECT COUNT(*) FROM universalis WHERE todayDate=:filterDate")
    fun countUniversalis(filterDate: Int = 0): Flow<Int>

    @Query(value = "SELECT * FROM ui_topic")
    fun getTopicEntities(): Flow<List<UITopicEntity>>

    @Transaction
    @Query(universalisByDate)
    fun getUniversalisByDate(
        filterDates: Int = 0,
    ): Flow<UniversalisExternal>

    @Transaction
    @Query(universalisByDate)
    fun getUniversalisOfToday(
        filterDates: Set<Int> = emptySet(),
    ): Flow<List<UniversalisExternal>>

    /**
     * Obtiene recursos **`MixtusExternal`** que coinciden con los parámetros de consulta.
     */

    @Transaction
    @Query(universalisByDateNew)
    fun getMixtusByDate(
        filterDate: Int = 0,
    ): Flow<MixtusExternal>

    @Transaction
    @Query(universalisByDateNew)
    fun getMixtusByDateNew(
        filterDate: Int,
    ): Flow<MixtusExternal>

    @Transaction
    @Query(universalisByDateNew)
    fun getOfficiumByDate(
        filterDate: Int = 0,
    ): Flow<OfficiumExternal>

    @Transaction
    @Query(universalisByDateNew)
    fun getLaudesByDate(
        filterDate: Int,
    ): Flow<LaudesExternal>

    @Transaction
    @Query(universalisByDateNew)
    fun getTertiamByDate(
        filterDate: Int,
    ): Flow<TertiamExternal>

    @Transaction
    @Query(universalisByDateNew)
    fun getSextamByDate(
        filterDate: Int,
    ): Flow<SextamExternal>

    @Transaction
    @Query(universalisByDateNew)
    fun getNonamByDate(
        filterDate: Int,
    ): Flow<NonamExternal>

    @Transaction
    @Query(universalisByDateNew)
    fun getVesperasByDate(
        filterDate: Int,
    )
            : Flow<PopulatedVesperasResource>

    @Transaction
    @Query(universalisByDateNew)
    fun getCompletoriumByDate(
        filterDate: Int,
    ): Flow<CompletoriumExternal>

    @Transaction
    @Query(universalisByDateNew)
    fun getMissaeLectionumByDate(
        filterDate: Int,
    ): Flow<MissaeLectionumExternal>

    @Transaction
    @Query(universalisByDateNew)
    fun getCommentariiByDate(
        filterDate: Int,
    ): Flow<CommentariiExternal>

    @Transaction
    @Query("SELECT * FROM saint WHERE theMonth=:theMonth AND theDay=:theDay")
    fun getSanctiiByDate(theMonth: Int?, theDay: Int?): Flow<SanctiiExternal>

    @Transaction
    @Query(universalisByDateNew)
    fun getHomiliaeByDate(
        filterDate: Int,
    ): Flow<HomiliaeExternal>

    @Transaction
    @Query(
        "SELECT * FROM rosarium_series_dies WHERE dies=:weekDay"
    )
    fun getRosariumByDate(weekDay: Int): Flow<RosariumExternal>

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
    fun getUniversalisByDatee(todayDate: Int): Flow<UniversalisEntity>


    /**
     * Inserts [topicEntities] into the db if they don't exist, and ignores those that do
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreUniversalis(topicEntities: List<UniversalisEntity>): List<Long>

    /**
     * Inserts or updates [entities] in the db under the specified primary keys
     */
    @Upsert
    suspend fun upsertUniversaliss(entities: List<UniversalisEntity>)

    @Upsert(entity = UniversalisEntity::class)
    suspend fun upsertUniversalis(entities: List<Universalis>)

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
