package org.deiverbum.app.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.core.database.model.entity.BibleBookEntity
import org.deiverbum.app.core.database.model.entity.BibleHomilyJoinEntity
import org.deiverbum.app.core.database.model.entity.BibleHomilyThemeEntity
import org.deiverbum.app.core.database.model.entity.BibleReadingEntity
import org.deiverbum.app.core.database.model.entity.EpigraphEntity
import org.deiverbum.app.core.database.model.entity.HomilyEntity
import org.deiverbum.app.core.database.model.entity.KyrieEntity
import org.deiverbum.app.core.database.model.entity.LHAntiphonEntity
import org.deiverbum.app.core.database.model.entity.LHAntiphonJoinEntity
import org.deiverbum.app.core.database.model.entity.LHGospelCanticleEntity
import org.deiverbum.app.core.database.model.entity.LHHymnEntity
import org.deiverbum.app.core.database.model.entity.LHHymnJoinEntity
import org.deiverbum.app.core.database.model.entity.LHIntercessionsEntity
import org.deiverbum.app.core.database.model.entity.LHIntercessionsJoinEntity
import org.deiverbum.app.core.database.model.entity.LHInvitatoryJoinEntity
import org.deiverbum.app.core.database.model.entity.LHKyrieJoinEntity
import org.deiverbum.app.core.database.model.entity.LHNightPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeBiblicalEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeBiblicalJoinEntity
import org.deiverbum.app.core.database.model.entity.LHOfficePatristicEntity
import org.deiverbum.app.core.database.model.entity.LHOfficePatristicJoinEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeVerseEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeVerseJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortJoinEntity
import org.deiverbum.app.core.database.model.entity.LHResponsoryEntity
import org.deiverbum.app.core.database.model.entity.LHResponsoryShortEntity
import org.deiverbum.app.core.database.model.entity.LHThemeEntity
import org.deiverbum.app.core.database.model.entity.LHVirginAntiphonJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyColorEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.LiturgyHomilyJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgySaintJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyTimeEntity
import org.deiverbum.app.core.database.model.entity.MassReadingEntity
import org.deiverbum.app.core.database.model.entity.MassReadingJoinEntity
import org.deiverbum.app.core.database.model.entity.PaterEntity
import org.deiverbum.app.core.database.model.entity.PaterOpusEntity
import org.deiverbum.app.core.database.model.entity.PrayerEntity
import org.deiverbum.app.core.database.model.entity.SaintEntity
import org.deiverbum.app.core.database.model.entity.SaintLifeEntity
import org.deiverbum.app.core.database.model.entity.SaintShortLifeEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.VirginAntiphonEntity
import org.deiverbum.app.core.database.model.external.CompletoriumExternal
import org.deiverbum.app.core.database.model.external.MissaeLectionumExternal
import org.deiverbum.app.core.database.model.relation.CommentariiLocal
import org.deiverbum.app.core.database.model.relation.HomiliaeLocal
import org.deiverbum.app.core.database.model.relation.LHLaudesLocal
import org.deiverbum.app.core.database.model.relation.LHMixtusLocal
import org.deiverbum.app.core.database.model.relation.LHNonamLocal
import org.deiverbum.app.core.database.model.relation.LHOfficiumLocal
import org.deiverbum.app.core.database.model.relation.LHSextamLocal
import org.deiverbum.app.core.database.model.relation.LHTertiamLocal
import org.deiverbum.app.core.database.model.relation.LHVesperasLocal
import org.deiverbum.app.core.database.model.relation.LocalOfficiumPascua
import org.deiverbum.app.core.database.model.relation.SanctiLocal
import org.deiverbum.app.core.model.alteri.Sanctus
import org.deiverbum.app.core.model.alteri.SanctusVita
import org.deiverbum.app.core.model.biblia.BibleBook
import org.deiverbum.app.core.model.biblia.LectioBiblica
import org.deiverbum.app.core.model.breviarium.LHAntiphon
import org.deiverbum.app.core.model.breviarium.LHAntiphonJoin
import org.deiverbum.app.core.model.breviarium.LHEpigraph
import org.deiverbum.app.core.model.breviarium.LHGospelCanticleTable
import org.deiverbum.app.core.model.breviarium.LHHymn
import org.deiverbum.app.core.model.breviarium.LHHymnJoin
import org.deiverbum.app.core.model.breviarium.LHIntercession
import org.deiverbum.app.core.model.breviarium.LHIntercessionsJoin
import org.deiverbum.app.core.model.breviarium.LHInvitatoryJoin
import org.deiverbum.app.core.model.breviarium.LHKyrieJoin
import org.deiverbum.app.core.model.breviarium.LHLectioBrevisTable
import org.deiverbum.app.core.model.breviarium.LHNightPrayer
import org.deiverbum.app.core.model.breviarium.LHOfficeBiblicalJoin
import org.deiverbum.app.core.model.breviarium.LHOfficeBiblicalTable
import org.deiverbum.app.core.model.breviarium.LHOfficePatristicJoin
import org.deiverbum.app.core.model.breviarium.LHOfficeVerse
import org.deiverbum.app.core.model.breviarium.LHOfficeVerseJoin
import org.deiverbum.app.core.model.breviarium.LHOfficiumLectioAltera
import org.deiverbum.app.core.model.breviarium.LHOratio
import org.deiverbum.app.core.model.breviarium.LHPsalm
import org.deiverbum.app.core.model.breviarium.LHPsalmJoin
import org.deiverbum.app.core.model.breviarium.LHPsalmodyJoin
import org.deiverbum.app.core.model.breviarium.LHReadingShortJoin
import org.deiverbum.app.core.model.breviarium.LHResponsoriumBrevis
import org.deiverbum.app.core.model.breviarium.LHResponsoryTable
import org.deiverbum.app.core.model.breviarium.LHTheme
import org.deiverbum.app.core.model.breviarium.LHVirginAntiphonJoin
import org.deiverbum.app.core.model.breviarium.SanctusVitaBrevis
import org.deiverbum.app.core.model.breviarium.VirginAntiphon
import org.deiverbum.app.core.model.data.crud.LHAntiphonJoinCrud
import org.deiverbum.app.core.model.data.crud.LHPsalmJoinCrud
import org.deiverbum.app.core.model.data.missae.MissaeLectionumJoin
import org.deiverbum.app.core.model.data.missae.MissaeLectionumTable
import org.deiverbum.app.core.model.data.traditio.BibleHomilyJoin
import org.deiverbum.app.core.model.data.traditio.BibleHomilyTheme
import org.deiverbum.app.core.model.data.traditio.Homily
import org.deiverbum.app.core.model.data.traditio.LiturgyHomilyJoin
import org.deiverbum.app.core.model.data.traditio.Pater
import org.deiverbum.app.core.model.data.traditio.PaterOpus
import org.deiverbum.app.core.model.liturgia.Kyrie
import org.deiverbum.app.core.model.liturgia.Liturgy
import org.deiverbum.app.core.model.liturgia.LiturgyColor
import org.deiverbum.app.core.model.liturgia.LiturgySaintJoin
import org.deiverbum.app.core.model.liturgia.LiturgyTime
import org.deiverbum.app.core.model.liturgia.Oratio
import org.deiverbum.app.core.model.sync.SyncStatus
import org.deiverbum.app.core.model.universalis.Universalis
import org.deiverbum.app.util.Constants


@Dao
interface TodayDao {
    companion object {
        const val universalisByDate = "SELECT * FROM universalis AS t WHERE t.todayDate =:theDate"

    }

    @Query("SELECT * from lh_hymn where hymnID=1")
    fun getHymById(): Flow<LHHymnEntity>

    /*
    @Insert(entity = TodayEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun todayInsertAll(today: List<Today>): List<Long>

    @Update(entity = TodayEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun todayUpdateAll(list: List<Today>)

    @Delete(entity = TodayEntity::class)
    fun todayDeleteAll(list: List<Today>)
*/
    @Insert(entity = UniversalisEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun universalisInsertAll(today: List<Universalis>): List<Long>

    @Update(entity = UniversalisEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun universalisUpdateAll(list: List<Universalis>)

    @Delete(entity = UniversalisEntity::class)
    fun universalisDeleteAll(list: List<Universalis>)

    @Transaction
    @Query(universalisByDate)
    fun getCommentariiByDate(theDate: Int): CommentariiLocal

    @Transaction
    @Query(universalisByDate)
    fun getOfficiumByDate(theDate: Int): LHOfficiumLocal

    @Transaction
    @Query("SELECT * FROM lh_easter_biblical_join WHERE groupID=:easterCode")
    //@Query("SELECT * FROM lh_easter_biblical WHERE groupFK=:easterCode ORDER BY theOrder")

    //@Query("SELECT * FROM lh_office_biblical_easter WHERE groupFK=:easterCode")
    //@Query("SELECT * FROM universalis u join lh_office_biblical_easter_copia j on u.oBiblicalFK=j.groupID WHERE j.groupID=:easterCode")
    //@Query("SELECT * FROM universalis u join lh_office_biblical_easter j on u.oBiblicalFK=j.groupFK  where groupFK=:easterCode")

    fun getOfficiumPascuaByDate(easterCode: Int): LocalOfficiumPascua

    @Transaction
    @Query("SELECT * FROM saint WHERE theMonth=:theMonth AND theDay=:theDay")
    fun getSanctiByDate(theMonth: Int?, theDay: Int?): SanctiLocal

    @Transaction
    @Query(universalisByDate)
    fun getMixtusByDate(theDate: Int?): LHMixtusLocal

    @Transaction
    @Query(universalisByDate)
    fun getLaudesByDate(theDate: Int?): LHLaudesLocal

    @Transaction
    @Query(universalisByDate)
    fun getTertiamByDate(theDate: Int?): LHTertiamLocal

    @Transaction
    @Query(universalisByDate)
    fun getSextamByDate(theDate: Int?): LHSextamLocal

    @Transaction
    @Query(universalisByDate)
    fun getNonamByDate(theDate: Int?): LHNonamLocal

    @Transaction
    @Query(universalisByDate)
    fun getVesperasByDate(theDate: Int?): LHVesperasLocal

    @Transaction
    @Query(universalisByDate)
    fun getCompletoriumByDate(theDate: Int?): CompletoriumExternal

    @Transaction
    @Query(universalisByDate)
    fun getHomiliaeByDate(theDate: Int?): HomiliaeLocal

    @Transaction
    @Query(universalisByDate)
    fun getMissaeLectionumByDate(theDate: Int?): MissaeLectionumExternal

    @get:Query("SELECT * FROM sync_status")
    val allSyncStatus: SyncStatus


    //@TypeConverters(MyTypeConverter::class)


    @Query("UPDATE sync_status SET lastUpdate=:lastUpdate")
    fun syncUpdate(lastUpdate: String)

    @Transaction
    @Query(
        "SELECT ss.lastUpdate,ss.version," +
                "(SELECT max(todayDate) FROM universalis) lastDate " +
                "FROM sync_status ss;"
    )
    fun syncInfo(): SyncStatus

    @Query("DELETE FROM universalis WHERE  substr(todayDate,1,4)+0<=:pastYear")
    fun deletePastYear(pastYear: Int): Int

    @Insert(entity = LiturgyEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun liturgyInsertAll(c: List<Liturgy>)

    @Update(entity = LiturgyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun liturgyUpdateAll(u: List<Liturgy>)

    @Delete(entity = LiturgyEntity::class)
    fun liturgyDeleteAll(d: List<Liturgy>)

    @Insert(entity = LiturgyHomilyJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun liturgyHomilyJoinInsertAll(list: List<LiturgyHomilyJoin>)

    @Update(entity = LiturgyHomilyJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun liturgyHomilyJoinUpdateAll(list: List<LiturgyHomilyJoin>)

    @Delete(entity = LiturgyHomilyJoinEntity::class)
    fun liturgyHomilyJoinDeleteAll(modelList: List<LiturgyHomilyJoin>)

    @Insert(entity = LiturgySaintJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun liturgySaintJoinInsertAll(list: List<LiturgySaintJoin>)

    @Update(entity = LiturgySaintJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun liturgySaintJoinUpdateAll(list: List<LiturgySaintJoin>)

    @Delete(entity = LiturgySaintJoinEntity::class)
    fun liturgySaintJoinDeleteAll(modelList: List<LiturgySaintJoin>)

    @Insert(entity = MassReadingEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun massReadingInsertAll(misaLectura: List<MissaeLectionumTable>)

    @Update(entity = MassReadingEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun massReadingUpdateAll(misaLectura: List<MissaeLectionumTable>)

    @Delete(entity = MassReadingEntity::class)
    fun massReadingDeleteAll(misaLectura: List<MissaeLectionumTable>)

    @Insert(entity = MassReadingJoinEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun massReadingJoinInsertAll(c: List<MissaeLectionumJoin>)

    @Update(entity = MassReadingJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun massReadingJoinUpdateAll(u: List<MissaeLectionumJoin>)

    @Delete(entity = MassReadingJoinEntity::class)
    fun massReadingJoinDeleteAll(d: List<MissaeLectionumJoin>)

    @Insert(entity = BibleReadingEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun bibleReadingInsertAll(bibleReading: List<LectioBiblica>)

    @Update(entity = BibleReadingEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun bibleReadingUpdateAll(list: List<LectioBiblica>)

    @Delete(entity = BibleReadingEntity::class)
    fun bibleReadingDeleteAll(modelList: List<LectioBiblica>)

    @Insert(entity = BibleHomilyJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun bibleHomilyJoinInsertAll(list: List<BibleHomilyJoin>)

    @Update(entity = BibleHomilyJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun bibleHomilyJoinUpdateAll(list: List<BibleHomilyJoin>)

    @Delete(entity = BibleHomilyJoinEntity::class)
    fun bibleHomilyJoinDeleteAll(modelList: List<BibleHomilyJoin>)

    @Insert(entity = BibleHomilyThemeEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun bibleHomilyThemeInsertAll(list: List<BibleHomilyTheme>)

    @Update(entity = BibleHomilyThemeEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun bibleHomilyThemeUpdateAll(list: List<BibleHomilyTheme>)

    @Delete(entity = BibleHomilyThemeEntity::class)
    fun bibleHomilyThemeDeleteAll(modelList: List<BibleHomilyTheme>)

    @Insert(entity = HomilyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun homilyInsertAll(list: List<Homily>)

    @Update(entity = HomilyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun homilyUpdateAll(list: List<Homily>)

    @Delete(entity = HomilyEntity::class)
    fun homilyDeleteAll(modelList: List<Homily>)

    @Insert(entity = LHInvitatoryJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhInvitatoryJoinInsertAll(c: List<LHInvitatoryJoin>)

    @Update(entity = LHInvitatoryJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhInvitatoryJoinUpdateAll(u: List<LHInvitatoryJoin>)

    @Delete(entity = LHInvitatoryJoinEntity::class)
    fun lhInvitatoryJoinDeleteAll(d: List<LHInvitatoryJoin>)

    @Insert(entity = SaintEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun saintInsertAll(c: List<Sanctus>)

    @Update(entity = SaintEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun saintUpdateAll(u: List<Sanctus>)

    @Delete(entity = SaintEntity::class)
    fun saintDeleteAll(d: List<Sanctus>)

    @Insert(entity = SaintShortLifeEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun saintShortLifeInsertAll(c: List<SanctusVitaBrevis>)

    @Update(entity = SaintShortLifeEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun saintShortLifeUpdateAll(u: List<SanctusVitaBrevis>)

    @Delete(entity = SaintShortLifeEntity::class)
    fun saintShortLifeDeleteAll(d: List<SanctusVitaBrevis>)

    @Insert(entity = SaintLifeEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun saintLifeInsertAll(c: List<SanctusVita>)

    @Update(entity = SaintLifeEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun saintLifeUpdateAll(u: List<SanctusVita>)

    @Delete(entity = SaintLifeEntity::class)
    fun saintLifeDeleteAll(d: List<SanctusVita>)

    @Insert(entity = LHHymnJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhHymnJoinInsertAll(c: List<LHHymnJoin>)

    @Update(entity = LHHymnJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhHymnJoinUpdateAll(u: List<LHHymnJoin>)

    @Delete(entity = LHHymnJoinEntity::class)
    fun lhHymnJoinDeleteAll(d: List<LHHymnJoin>)

    @Insert(entity = LHOfficeVerseEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhOfficeVerseInsertAll(c: List<LHOfficeVerse>)

    @Update(entity = LHOfficeVerseEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhOfficeVerseUpdateAll(u: List<LHOfficeVerse>)

    @Delete(entity = LHOfficeVerseEntity::class)
    fun lhOfficeVerseDeleteAll(d: List<LHOfficeVerse>)

    @Insert(entity = LHOfficeVerseJoinEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhOfficeVerseJoinInsertAll(c: List<LHOfficeVerseJoin>)

    @Update(entity = LHOfficeVerseJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhOfficeVerseJoinUpdateAll(u: List<LHOfficeVerseJoin>)

    @Delete(entity = LHOfficeVerseJoinEntity::class)
    fun lhOfficeVerseJoinDeleteAll(d: List<LHOfficeVerseJoin>)

    @Insert(entity = LHHymnEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhHymnInsertAll(c: List<LHHymn>)

    @Update(entity = LHHymnEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhHymnUpdateAll(u: List<LHHymn>)

    @Delete(entity = LHHymnEntity::class)
    fun lhHymnDeleteAll(d: List<LHHymn>)

    @Insert(entity = PaterEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun paterInsertAll(c: List<Pater>)

    @Update(entity = PaterEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun paterUpdateAll(u: List<Pater>)

    @Delete(entity = PaterEntity::class)
    fun paterDeleteAll(d: List<Pater>)

    @Insert(entity = PaterOpusEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun paterOpusInsertAll(c: List<PaterOpus>)

    @Update(entity = PaterOpusEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun paterOpusUpdateAll(u: List<PaterOpus>)

    @Delete(entity = PaterOpusEntity::class)
    fun paterOpusDeleteAll(d: List<PaterOpus>)

    /*
        @Insert(entity = LHPsalmodyEntity::class, onConflict = OnConflictStrategy.IGNORE)
        fun lhPsalmodyInsertAll(c: List<LHPsalmody>)

        @Update(entity = LHPsalmodyEntity::class, onConflict = OnConflictStrategy.REPLACE)
        fun lhPsalmodyUpdateAll(u: List<LHPsalmody>)

        @Delete(entity = LHPsalmodyEntity::class)
        fun lhPsalmodyDeleteAll(d: List<LHPsalmody>)
    */
    @Insert(entity = LHPsalmodyJoinEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhPsalmodyJoinInsertAll(c: List<LHPsalmodyJoin>)

    @Update(entity = LHPsalmodyJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhPsalmodyJoinUpdateAll(u: List<LHPsalmodyJoin>)

    @Delete(entity = LHPsalmodyJoinEntity::class)
    fun lhPsalmodyJoinDeleteAll(d: List<LHPsalmodyJoin>)

    @Insert(entity = LHPsalmJoinEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhPsalmJoinInsertAll(c: List<LHPsalmJoin>)

    @Update(entity = LHPsalmJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhPsalmJoinUpdateAll(u: List<LHPsalmJoin>): Int

    @Delete(entity = LHPsalmJoinEntity::class)
    fun lhPsalmJoinDeleteAll(d: List<LHPsalmJoin>): Int

    @Delete(entity = LHAntiphonJoinEntity::class)
    fun lhAntiphonJoinDeleteAll(d: List<LHAntiphonJoin>)

    @Insert(entity = LHAntiphonJoinEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhAntiphonJoinInsertAll(c: List<LHAntiphonJoin>)


    @Query("DELETE FROM ${Constants.LH_ANTIPHON_JOIN} WHERE groupFK=:groupFK AND antiphonFK=:antiphonFK AND theOrder=:theOrder")
    fun deleteLHAntiphonJoin(groupFK: Int, antiphonFK: Int, theOrder: Int)

    @Transaction
    @Query("")
    fun deleteLHAntiphonJoinAll(list: List<LHAntiphonJoinCrud>) {
        for (p in list) {
            deleteLHAntiphonJoin(p.groupFKOld, p.antiphonFKOld, p.theOrderOld)
        }
    }

    @Query("UPDATE ${Constants.LH_ANTIPHON_JOIN} SET groupFK=:groupFKNew, antiphonFK=:antiphonFKNew, theOrder=:theOrderNew WHERE groupFK=:groupFKOld AND antiphonFK=:antiphonFKOld AND theOrder=:theOrderOld")
    fun updateLHAntiphonJoin(
        groupFKNew: Int,
        antiphonFKNew: Int,
        theOrderNew: Int,
        groupFKOld: Int,
        antiphonFKOld: Int,
        theOrderOld: Int
    )

    @Transaction
    @Delete(entity = LHAntiphonJoinEntity::class)
    fun updateLHAntiphonJoinAll(list: List<LHAntiphonJoinCrud>) {
        for (p in list) {
            updateLHAntiphonJoin(
                p.groupFKNew,
                p.antiphonFKNew,
                p.theOrderNew,
                p.groupFKOld,
                p.antiphonFKOld,
                p.theOrderOld
            )
        }
    }


    @Query(
        "UPDATE ${Constants.LH_PSALM_JOIN} SET groupFK=:groupFKNew, readingFK=:readingFKNew, theOrder=:theOrderNew, themeFK=:themeFKNew, epigraphFK=:epigraphFKNew,thePart=:thePartNew " +
                "WHERE groupFK=:groupFKOld AND readingFK=:readingFKOld AND theOrder=:theOrderOld"
    )
    fun updateLHPsalmJoin(
        groupFKNew: Int,
        readingFKNew: Int,
        theOrderNew: Int,
        themeFKNew: Int?,
        epigraphFKNew: Int?,
        thePartNew: Int?,
        groupFKOld: Int,
        readingFKOld: Int,
        theOrderOld: Int
    )

    @Transaction
    @Update(entity = LHPsalmJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun updateLHPsalmJoinAll(list: List<LHPsalmJoinCrud>) {
        for (p in list) {
            updateLHPsalmJoin(
                p.groupFKNew,
                p.readingFKNew,
                p.theOrderNew,
                p.themeFKNew,
                p.epigraphFKNew,
                p.thePartNew,
                p.groupFKOld,
                p.readingFKOld,
                p.theOrderOld
            )
        }
    }

    @Query(
        "DELETE FROM ${Constants.LH_PSALM_JOIN}  " +
                "WHERE groupFK=:groupFKOld AND readingFK=:readingFKOld AND theOrder=:theOrderOld AND themeFK=:themeFKOld AND epigraphFK=:epigraphFKOld AND thePart=:thePartOld"
    )
    fun deleteLHPsalmJoin(
        groupFKOld: Int,
        readingFKOld: Int,
        theOrderOld: Int,
        themeFKOld: Int,
        epigraphFKOld: Int,
        thePartOld: Int
    )

    @Transaction
    @Delete(entity = LHPsalmJoinEntity::class)

    fun deleteLHPsalmJoinAll(list: List<LHPsalmJoinCrud>) {
        for (p in list) {
            deleteLHPsalmJoin(
                p.groupFKOld,
                p.readingFKOld,
                p.theOrderOld,
                p.themeFKOld!!,
                p.epigraphFKOld!!,
                p.thePartOld!!
            )
        }
    }

    @Update(entity = LHAntiphonJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhAntiphonJoinUpdateAll(u: List<LHAntiphonJoin>)


    @Insert(entity = LHAntiphonEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhAntiphonInsertAll(c: List<LHAntiphon>)

    @Update(entity = LHAntiphonEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhAntiphonUpdateAll(u: List<LHAntiphon>)

    @Delete(entity = LHAntiphonEntity::class)
    fun lhAntiphonDeleteAll(d: List<LHAntiphon>)

    @Insert(entity = LHThemeEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhThemeInsertAll(c: List<LHTheme>)

    @Update(entity = LHThemeEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhThemeUpdateAll(u: List<LHTheme>)

    @Delete(entity = LHThemeEntity::class)
    fun lhThemeDeleteAll(d: List<LHTheme>)

    @Insert(entity = EpigraphEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhEpigraphInsertAll(c: List<LHEpigraph>)

    @Update(entity = EpigraphEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhEpigraphUpdateAll(u: List<LHEpigraph>)

    @Delete(entity = EpigraphEntity::class)
    fun lhEpigraphDeleteAll(d: List<LHEpigraph>)

    @Insert(entity = LHPsalmEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhPsalmInsertAll(c: List<LHPsalm>)

    @Update(entity = LHPsalmEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhPsalmUpdateAll(u: List<LHPsalm>)

    @Delete(entity = LHPsalmEntity::class)
    fun lhPsalmDeleteAll(d: List<LHPsalm>)

    @Insert(entity = LHOfficeBiblicalEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhOfficeBiblicalInsertAll(c: List<LHOfficeBiblicalTable>)

    @Update(entity = LHOfficeBiblicalEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhOfficeBiblicalUpdateAll(u: List<LHOfficeBiblicalTable>)

    @Delete(entity = LHOfficeBiblicalEntity::class)
    fun lhOfficeBiblicalDeleteAll(d: List<LHOfficeBiblicalTable>)

    @Insert(entity = LHOfficeBiblicalJoinEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhOfficeBiblicalJoinInsertAll(c: List<LHOfficeBiblicalJoin>)

    @Update(entity = LHOfficeBiblicalJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhOfficeBiblicalJoinUpdateAll(u: List<LHOfficeBiblicalJoin>)

    @Delete(entity = LHOfficeBiblicalJoinEntity::class)
    fun lhOfficeBiblicalJoinDeleteAll(d: List<LHOfficeBiblicalJoin>)

    @Insert(entity = LHResponsoryEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhResponsoryInsertAll(c: List<LHResponsoryTable>)

    @Update(entity = LHResponsoryEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhResponsoryUpdateAll(u: List<LHResponsoryTable>)

    @Delete(entity = LHResponsoryEntity::class)
    fun lhResponsoryDeleteAll(d: List<LHResponsoryTable>)

    @Insert(entity = LHOfficePatristicEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhOfficePatristicInsertAll(c: List<LHOfficiumLectioAltera>)

    @Update(entity = LHOfficePatristicEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhOfficePatristicUpdateAll(u: List<LHOfficiumLectioAltera>)

    @Delete(entity = LHOfficePatristicEntity::class)
    fun lhOfficePatristicDeleteAll(d: List<LHOfficiumLectioAltera>)

    @Insert(entity = LHOfficePatristicJoinEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhOfficePatristicJoinInsertAll(c: List<LHOfficePatristicJoin>)

    @Update(entity = LHOfficePatristicJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhOfficePatristicJoinUpdateAll(u: List<LHOfficePatristicJoin>)

    @Delete(entity = LHOfficePatristicJoinEntity::class)
    fun lhOfficePatristicJoinDeleteAll(d: List<LHOfficePatristicJoin>)

    @Insert(entity = LHReadingShortEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhReadingShortInsertAll(c: List<LHLectioBrevisTable>)

    @Update(entity = LHReadingShortEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhReadingShortUpdateAll(u: List<LHLectioBrevisTable>)

    @Delete(entity = LHReadingShortEntity::class)
    fun lhReadingShortDeleteAll(d: List<LHLectioBrevisTable>)

    @Insert(entity = LHResponsoryShortEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhResponsoryShortInsertAll(c: List<LHResponsoriumBrevis>)

    @Update(entity = LHResponsoryShortEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhResponsoryShortUpdateAll(u: List<LHResponsoriumBrevis>)

    @Delete(entity = LHResponsoryShortEntity::class)
    fun lhResponsoryShortDeleteAll(d: List<LHResponsoriumBrevis>)

    @Insert(entity = LHReadingShortJoinEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhReadingShortJoinInsertAll(c: List<LHReadingShortJoin>)

    @Update(entity = LHReadingShortJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhReadingShortJoinUpdateAll(u: List<LHReadingShortJoin>)

    @Delete(entity = LHReadingShortJoinEntity::class)
    fun lhReadingShortJoinDeleteAll(d: List<LHReadingShortJoin>)

    @Insert(entity = LHGospelCanticleEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun gospelCanticleInsertAll(list: List<LHGospelCanticleTable>)

    @Update(entity = LHGospelCanticleEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun gospelCanticleUpdateAll(list: List<LHGospelCanticleTable>)

    @Delete(entity = LHGospelCanticleEntity::class)
    fun gospelCanticleDeleteAll(modelList: List<LHGospelCanticleTable>)

    @Insert(entity = LHIntercessionsEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhIntercessionsInsertAll(list: List<LHIntercession>)

    @Update(entity = LHIntercessionsEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhIntercessionsUpdateAll(list: List<LHIntercession>)

    @Delete(entity = LHIntercessionsEntity::class)
    fun lhIntercessionsDeleteAll(list: List<LHIntercession>)

    @Insert(entity = LHIntercessionsJoinEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhIntercessionsJoinInsertAll(list: List<LHIntercessionsJoin>)

    @Update(entity = LHIntercessionsJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhIntercessionsJoinUpdateAll(list: List<LHIntercessionsJoin>)

    @Delete(entity = LHIntercessionsJoinEntity::class)
    fun lhIntercessionsJoinDeleteAll(list: List<LHIntercessionsJoin>)

    @Insert(entity = PrayerEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun prayerInsertAll(c: List<Oratio>)

    @Update(entity = PrayerEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun prayerUpdateAll(u: List<Oratio>)

    @Delete(entity = PrayerEntity::class)
    fun prayerDeleteAll(d: List<Oratio>)

    @Insert(entity = LHPrayerEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhPrayerInsertAll(c: List<LHOratio>)

    @Update(entity = LHPrayerEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhPrayerUpdateAll(u: List<LHOratio>)

    @Delete(entity = LHPrayerEntity::class)
    fun lhPrayerDeleteAll(d: List<LHOratio>)

    @Insert(entity = BibleBookEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun bibleBookInsertAll(c: List<BibleBook>)

    @Update(entity = BibleBookEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun bibleBookUpdateAll(u: List<BibleBook>)

    @Delete(entity = BibleBookEntity::class)
    fun bibleBookDeleteAll(d: List<BibleBook>)

    @Insert(entity = KyrieEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun kyrieInsertAll(c: List<Kyrie>)

    @Update(entity = KyrieEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun kyrieUpdateAll(u: List<Kyrie>)

    @Delete(entity = KyrieEntity::class)
    fun kyrieDeleteAll(d: List<Kyrie>)

    @Insert(entity = LHKyrieJoinEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhKyrieJoinInsertAll(c: List<LHKyrieJoin>)

    @Update(entity = LHKyrieJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhKyrieJoinUpdateAll(u: List<LHKyrieJoin>)

    @Delete(entity = LHKyrieJoinEntity::class)
    fun lhKyrieJoinDeleteAll(d: List<LHKyrieJoin>)

    @Insert(entity = LHNightPrayerEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhNightPrayerInsertAll(c: List<LHNightPrayer>)

    @Update(entity = LHNightPrayerEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhNightPrayerUpdateAll(u: List<LHNightPrayer>)

    @Delete(entity = LHNightPrayerEntity::class)
    fun lhNightPrayerDeleteAll(d: List<LHNightPrayer>)

    @Insert(entity = LHVirginAntiphonJoinEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhVirginAntiphonJoinInsertAll(c: List<LHVirginAntiphonJoin>)

    @Update(entity = LHVirginAntiphonJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhVirginAntiphonJoinUpdateAll(u: List<LHVirginAntiphonJoin>)

    @Delete(entity = LHVirginAntiphonJoinEntity::class)
    fun lhVirginAntiphonJoinDeleteAll(d: List<LHVirginAntiphonJoin>)

    @Insert(entity = LiturgyColorEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun liturgyColorInsertAll(c: List<LiturgyColor>)

    @Update(entity = LiturgyColorEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun liturgyColorUpdateAll(u: List<LiturgyColor>)

    @Delete(entity = LiturgyColorEntity::class)
    fun liturgyColorDeleteAll(d: List<LiturgyColor>)

    @Insert(entity = LiturgyTimeEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun liturgyTimeInsertAll(c: List<LiturgyTime>)

    @Update(entity = LiturgyTimeEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun liturgyTimeUpdateAll(u: List<LiturgyTime>)

    @Delete(entity = LiturgyTimeEntity::class)
    fun liturgyTimeDeleteAll(d: List<LiturgyTime>)

    @Insert(entity = VirginAntiphonEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun virginAntiphonInsertAll(c: List<VirginAntiphon>)

    @Update(entity = VirginAntiphonEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun virginAntiphonUpdateAll(u: List<VirginAntiphon>)

    @Delete(entity = VirginAntiphonEntity::class)
    fun virginAntiphonDeleteAll(d: List<VirginAntiphon>)
}