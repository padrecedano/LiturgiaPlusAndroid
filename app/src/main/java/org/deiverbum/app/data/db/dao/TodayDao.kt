package org.deiverbum.app.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.deiverbum.app.data.entity.*
import org.deiverbum.app.data.entity.relation.*
import org.deiverbum.app.model.*
import org.deiverbum.app.util.Constants

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Dao
interface TodayDao {
    @Query("SELECT * FROM today WHERE todayDate =  :todayDate LIMIT 1")
    fun findByDate(todayDate: Int?): LiveData<Today?>?

    @get:Query("SELECT * FROM sync_status")
    val allSyncStatus: SyncStatus?

    @Query("SELECT todayDate FROM today ORDER BY todayDate DESC LIMIT 1")
    fun findLastDate(): Int?

    @Query("SELECT todayDate FROM today ORDER BY todayDate DESC LIMIT 1")
    fun findLastToday(): Int?

    @Query("SELECT liturgyID FROM liturgy ORDER BY liturgyID DESC LIMIT 1")
    fun findLastLiturgia(): Int?

    @Query("SELECT homilyID FROM homily ORDER BY homilyID DESC LIMIT 1")
    fun findLastHomilia(): Int?

    @Insert(entity = TodayEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTodays(today: List<Today?>?)


    @Insert(entity = LiturgyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun liturgiaInsertAll(today: List<Liturgy?>?)

    @Insert(entity = HomilyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun homiliaInsertAll(list: List<HomilyListDao?>?)

    @Insert(entity = LHHymnEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun insertAllHimnos(today: List<LHHymn?>?)

    @Insert(entity = TodayEntity::class)
    fun insertTodayX(today: Today?)

    @Transaction
    @Query(todayByDate)
    fun getOficioOfToday(theDate: Int?): TodayOficio?

    @Transaction
    @Query(todayByDate)
    fun getLaudesOfToday(theDate: Int?): TodayLaudes?

    @Transaction
    @Query(todayByDate)
    fun getTerciaOfToday(theDate: Int?): TodayTercia?

    @Transaction
    @Query(todayByDate)
    fun getSextaOfToday(theDate: Int?): TodaySexta?

    @Transaction
    @Query(todayByDate)
    fun getNonaOfToday(theDate: Int?): TodayNona?

    @Transaction
    @Query(todayByDate)
    fun getMixtoOfToday(theDate: Int?): TodayMixto?

    @Transaction
    @Query(todayByDate)
    fun getVisperasOfToday(theDate: Int?): TodayVisperas?

    @Transaction
    @Query(todayByDate)
    fun getCompletasOfToday(theDate: Int?): TodayCompletas?

    @Transaction
    @Query(todayByDate)
    fun getMisaLecturas(theDate: Int?): TodayMisaLecturas?

    @Transaction
    @Query(sqlSaint)
    fun getSantoOfToday(theMonth: Int, theDay: Int): TodaySanto?

    @Transaction
    @Query(todayByDate)
    fun getHomilias(theDate: Int?): TodayHomilias?

    @Transaction
    @Query(
        "SELECT ss.lastUpdate,ss.versionDB," +
                "(SELECT max(todayDate) FROM today) tableName " +
                "FROM sync_status ss;"
    )
    //@get
    //@Query("SELECT * FROM sync_status")

    fun syncInfo(): LiveData<SyncStatus?>

    //val syncInfo: LiveData<SyncStatus?>?

    //@Query("SELECT COUNT(*) FROM sync_status")
    //LiveData<Integer> syncStatusCount();
    @Query("SELECT COUNT(*) FROM sync_status")
    fun syncStatusCount(): Int

    @Transaction
    /*@Query("SELECT * FROM today AS t " +
            "JOIN homilia h ON t.feriaFK=h.homiliaId JOIN misa_lectura ml ON t.mLecturasFK=ml.liturgiaFK " +
            "WHERE t.hoy =:theDate AND ml.orden >= 40")*/
    /*@Query("SELECT * FROM today AS t " +
            "JOIN mass_reading mr ON t.massReadingFK=mr.liturgyFK "+
            "JOIN bible_homily_join bhj ON bhj.readingFK=mr.readingFK "+
            "JOIN homily h ON bhj.homilyFK=h.homilyID "+
            "WHERE t.todayDate =:theDate AND mr.`theOrder` >= 40")*/
    @Query(todayByDate)
    fun getComentarios(theDate: Int?): TodayComentarios?

    @Insert(entity = LHReadingShortJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun biblicaBreveJoinInsertAll(list: List<LHReadingShortJoin?>?)

    @Update(entity = LHAntiphonEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhAntifonaUpdateAll(list: List<LHAntiphon?>?)

    @Insert(entity = TodayEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun todayInsertAll(today: List<Today?>?)

    @Update(entity = TodayEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun todayUpdateAll(list: List<Today?>?)

    @Delete(entity = TodayEntity::class)
    fun todayDeleteAll(list: List<Today?>?)

    @Insert(entity = MassReadingEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun massReadingInsertAll(misaLectura: List<MassReadingTable?>?)

    @Update(entity = MassReadingEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun massReadingUpdateAll(misaLectura: List<MassReadingTable?>?)

    @Delete(entity = MassReadingEntity::class)
    fun massReadingDeleteAll(misaLectura: List<MassReadingTable?>?)

    @Insert(entity = MassReadingJoinEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun massReadingJoinInsertAll(c: List<MassReadingJoin?>?)

    @Update(entity = MassReadingJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun massReadingJoinUpdateAll(u: List<MassReadingJoin?>?)

    @Delete(entity = MassReadingJoinEntity::class)
    fun massReadingJoinDeleteAll(d: List<MassReadingJoin?>?)

    @Insert(entity = BibleReadingEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun bibleReadingInsertAll(bibleReading: List<Biblical?>?)

    @Update(entity = BibleReadingEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun bibleReadingUpdateAll(list: List<Biblical?>?)

    @Delete(entity = BibleReadingEntity::class)
    fun bibleReadingDeleteAll(modelList: List<Biblical?>?)

    @Query("SELECT lastUpdate FROM sync_status WHERE tableName=:tableName LIMIT 1")
    fun syncLastVersion(tableName: String?): String?

    @Query("SELECT MAX(lastUpdate) FROM sync_status")
    fun liveLastSync(): LiveData<String?>?

    @Query("SELECT MAX(todayDate) FROM today")
    fun liveLastToday(): LiveData<String?>?

    @get:Query("SELECT MAX(lastUpdate) FROM sync_status")
    val lastUpdate: String?

    @Update(entity = SyncStatusEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun syncUpdateAll(list: List<SyncStatus?>?)

    @Query("INSERT INTO sync_status (tableName,versionDB)VALUES (:initial,1)")
    fun insertSyncStatus(initial: String?)

    @Insert(entity = LiturgyEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun liturgyInsertAll(c: List<Liturgy?>?)

    @Update(entity = LiturgyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun liturgyUpdateAll(u: List<Liturgy?>?)

    @Delete(entity = LiturgyEntity::class)
    fun liturgyDeleteAll(d: List<Liturgy?>?)

    @Insert(entity = LiturgyHomilyJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun liturgyHomilyJoinInsertAll(list: List<LiturgyHomilyJoin?>?)

    @Update(entity = LiturgyHomilyJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun liturgyHomilyJoinUpdateAll(list: List<LiturgyHomilyJoin?>?)

    @Delete(entity = LiturgyHomilyJoinEntity::class)
    fun liturgyHomilyJoinDeleteAll(modelList: List<LiturgyHomilyJoin?>?)

    @Insert(entity = LiturgySaintJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun liturgySaintJoinInsertAll(list: List<LiturgySaintJoin?>?)

    @Update(entity = LiturgySaintJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun liturgySaintJoinUpdateAll(list: List<LiturgySaintJoin?>?)

    @Delete(entity = LiturgySaintJoinEntity::class)
    fun liturgySaintJoinDeleteAll(modelList: List<LiturgySaintJoin?>?)

    @Query("UPDATE sync_status SET lastUpdate=:lastUpdate")
    fun syncUpdate(lastUpdate: String?)

    @Insert(entity = BibleHomilyJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun bibleHomilyJoinInsertAll(list: List<BibleHomilyJoin?>?)

    @Update(entity = BibleHomilyJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun bibleHomilyJoinUpdateAll(list: List<BibleHomilyJoin?>?)

    @Delete(entity = BibleHomilyJoinEntity::class)
    fun bibleHomilyJoinDeleteAll(modelList: List<BibleHomilyJoin?>?)

    @Insert(entity = BibleHomilyThemeEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun bibleHomilyThemeInsertAll(list: List<BibleHomilyTheme?>?)

    @Update(entity = BibleHomilyThemeEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun bibleHomilyThemeUpdateAll(list: List<BibleHomilyTheme?>?)

    @Delete(entity = BibleHomilyThemeEntity::class)
    fun bibleHomilyThemeDeleteAll(modelList: List<BibleHomilyTheme?>?)

    @Insert(entity = HomilyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun homilyInsertAll(list: List<Homily?>?)

    @Update(entity = HomilyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun homilyUpdateAll(list: List<Homily?>?)

    @Delete(entity = HomilyEntity::class)
    fun homilyDeleteAll(modelList: List<Homily?>?)

    @Insert(entity = LHInvitatoryJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhInvitatoryJoinInsertAll(c: List<LHInvitatoryJoin?>?)

    @Update(entity = LHInvitatoryJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhInvitatoryJoinUpdateAll(u: List<LHInvitatoryJoin?>?)

    @Delete(entity = LHInvitatoryJoinEntity::class)
    fun lhInvitatoryJoinDeleteAll(d: List<LHInvitatoryJoin?>?)

    @Insert(entity = SaintEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun saintInsertAll(c: List<Saint?>?)

    @Update(entity = SaintEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun saintUpdateAll(u: List<Saint?>?)

    @Delete(entity = SaintEntity::class)
    fun saintDeleteAll(d: List<Saint?>?)

    @Insert(entity = SaintShortLifeEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun saintShortLifeInsertAll(c: List<SaintShortLife?>?)

    @Update(entity = SaintShortLifeEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun saintShortLifeUpdateAll(u: List<SaintShortLife?>?)

    @Delete(entity = SaintShortLifeEntity::class)
    fun saintShortLifeDeleteAll(d: List<SaintShortLife?>?)

    @Insert(entity = SaintLifeEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun saintLifeInsertAll(c: List<SaintLife?>?)

    @Update(entity = SaintLifeEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun saintLifeUpdateAll(u: List<SaintLife?>?)

    @Delete(entity = SaintLifeEntity::class)
    fun saintLifeDeleteAll(d: List<SaintLife?>?)

    @Insert(entity = LHHymnJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhHymnJoinInsertAll(c: List<LHHymnJoin?>?)

    @Update(entity = LHHymnJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhHymnJoinUpdateAll(u: List<LHHymnJoin?>?)

    @Delete(entity = LHHymnJoinEntity::class)
    fun lhHymnJoinDeleteAll(d: List<LHHymnJoin?>?)

    @Insert(entity = LHOfficeVerseEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhOfficeVerseInsertAll(c: List<LHOfficeVerse?>?)

    @Update(entity = LHOfficeVerseEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhOfficeVerseUpdateAll(u: List<LHOfficeVerse?>?)

    @Delete(entity = LHOfficeVerseEntity::class)
    fun lhOfficeVerseDeleteAll(d: List<LHOfficeVerse?>?)

    @Insert(entity = LHOfficeVerseJoinEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhOfficeVerseJoinInsertAll(c: List<LHOfficeVerseJoin?>?)

    @Update(entity = LHOfficeVerseJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhOfficeVerseJoinUpdateAll(u: List<LHOfficeVerseJoin?>?)

    @Delete(entity = LHOfficeVerseJoinEntity::class)
    fun lhOfficeVerseJoinDeleteAll(d: List<LHOfficeVerseJoin?>?)

    @Insert(entity = LHHymnEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhHymnInsertAll(c: List<LHHymn?>?)

    @Update(entity = LHHymnEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhHymnUpdateAll(u: List<LHHymn?>?)

    @Delete(entity = LHHymnEntity::class)
    fun lhHymnDeleteAll(d: List<LHHymn?>?)

    @Insert(entity = PaterEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun paterInsertAll(c: List<Pater?>?)

    @Update(entity = PaterEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun paterUpdateAll(u: List<Pater?>?)

    @Delete(entity = PaterEntity::class)
    fun paterDeleteAll(d: List<Pater?>?)

    @Insert(entity = PaterOpusEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun paterOpusInsertAll(c: List<PaterOpus?>?)

    @Update(entity = PaterOpusEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun paterOpusUpdateAll(u: List<PaterOpus?>?)

    @Delete(entity = PaterOpusEntity::class)
    fun paterOpusDeleteAll(d: List<PaterOpus?>?)

    @Insert(entity = LHPsalmodyEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhPsalmodyInsertAll(c: List<LHPsalmody?>?)

    @Update(entity = LHPsalmodyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhPsalmodyUpdateAll(u: List<LHPsalmody?>?)

    @Delete(entity = LHPsalmodyEntity::class)
    fun lhPsalmodyDeleteAll(d: List<LHPsalmody?>?)

    @Insert(entity = LHPsalmodyJoinEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhPsalmodyJoinInsertAll(c: List<LHPsalmodyJoin?>?)

    @Update(entity = LHPsalmodyJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhPsalmodyJoinUpdateAll(u: List<LHPsalmodyJoin?>?)

    @Delete(entity = LHPsalmodyJoinEntity::class)
    fun lhPsalmodyJoinDeleteAll(d: List<LHPsalmodyJoin?>?)

    @Insert(entity = LHAntiphonEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhAntiphonInsertAll(c: List<LHAntiphon?>?)

    @Update(entity = LHAntiphonEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhAntiphonUpdateAll(u: List<LHAntiphon?>?)

    @Delete(entity = LHAntiphonEntity::class)
    fun lhAntiphonDeleteAll(d: List<LHAntiphon?>?)

    @Insert(entity = LHThemeEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhThemeInsertAll(c: List<LHTheme?>?)

    @Update(entity = LHThemeEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhThemeUpdateAll(u: List<LHTheme?>?)

    @Delete(entity = LHThemeEntity::class)
    fun lhThemeDeleteAll(d: List<LHTheme?>?)

    @Insert(entity = EpigraphEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhEpigraphInsertAll(c: List<LHEpigraph?>?)

    @Update(entity = EpigraphEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhEpigraphUpdateAll(u: List<LHEpigraph?>?)

    @Delete(entity = EpigraphEntity::class)
    fun lhEpigraphDeleteAll(d: List<LHEpigraph?>?)

    @Insert(entity = PsalmEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhPsalmInsertAll(c: List<LHPsalm?>?)

    @Update(entity = PsalmEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhPsalmUpdateAll(u: List<LHPsalm?>?)

    @Delete(entity = PsalmEntity::class)
    fun lhPsalmDeleteAll(d: List<LHPsalm?>?)

    @Insert(entity = LHOfficeBiblicalEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhOfficeBiblicalInsertAll(c: List<LHOfficeBiblicalTable?>?)

    @Update(entity = LHOfficeBiblicalEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhOfficeBiblicalUpdateAll(u: List<LHOfficeBiblicalTable?>?)

    @Delete(entity = LHOfficeBiblicalEntity::class)
    fun lhOfficeBiblicalDeleteAll(d: List<LHOfficeBiblicalTable?>?)

    @Insert(entity = LHOfficeBiblicalJoinEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhOfficeBiblicalJoinInsertAll(c: List<LHOfficeBiblicalJoin?>?)

    @Update(entity = LHOfficeBiblicalJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhOfficeBiblicalJoinUpdateAll(u: List<LHOfficeBiblicalJoin?>?)

    @Delete(entity = LHOfficeBiblicalJoinEntity::class)
    fun lhOfficeBiblicalJoinDeleteAll(d: List<LHOfficeBiblicalJoin?>?)

    @Insert(entity = LHResponsoryEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhResponsoryInsertAll(c: List<LHResponsoryTable?>?)

    @Update(entity = LHResponsoryEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhResponsoryUpdateAll(u: List<LHResponsoryTable?>?)

    @Delete(entity = LHResponsoryEntity::class)
    fun lhResponsoryDeleteAll(d: List<LHResponsoryTable?>?)

    @Insert(entity = LHOfficePatristicEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhOfficePatristicInsertAll(c: List<LHOfficePatristic?>?)

    @Update(entity = LHOfficePatristicEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhOfficePatristicUpdateAll(u: List<LHOfficePatristic?>?)

    @Delete(entity = LHOfficePatristicEntity::class)
    fun lhOfficePatristicDeleteAll(d: List<LHOfficePatristic?>?)

    @Insert(entity = LHOfficePatristicJoinEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhOfficePatristicJoinInsertAll(c: List<LHOfficePatristicJoin?>?)

    @Update(entity = LHOfficePatristicJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhOfficePatristicJoinUpdateAll(u: List<LHOfficePatristicJoin?>?)

    @Delete(entity = LHOfficePatristicJoinEntity::class)
    fun lhOfficePatristicJoinDeleteAll(d: List<LHOfficePatristicJoin?>?)

    @Insert(entity = LHReadingShortEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhReadingShortInsertAll(c: List<LHReadingShort?>?)

    @Update(entity = LHReadingShortEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhReadingShortUpdateAll(u: List<LHReadingShort?>?)

    @Delete(entity = LHReadingShortEntity::class)
    fun lhReadingShortDeleteAll(d: List<LHReadingShort?>?)

    @Insert(entity = LHResponsoryShortEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhResponsoryShortInsertAll(c: List<LHResponsoryShort?>?)

    @Update(entity = LHResponsoryShortEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhResponsoryShortUpdateAll(u: List<LHResponsoryShort?>?)

    @Delete(entity = LHResponsoryShortEntity::class)
    fun lhResponsoryShortDeleteAll(d: List<LHResponsoryShort?>?)

    @Insert(entity = LHReadingShortJoinEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhReadingShortJoinInsertAll(c: List<LHReadingShortJoin?>?)

    @Update(entity = LHReadingShortJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhReadingShortJoinUpdateAll(u: List<LHReadingShortJoin?>?)

    @Delete(entity = LHReadingShortJoinEntity::class)
    fun lhReadingShortJoinDeleteAll(d: List<LHReadingShortJoin?>?)

    @Insert(entity = LHGospelCanticleEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun gospelCanticleInsertAll(list: List<LHGospelCanticleTable?>?)

    @Update(entity = LHGospelCanticleEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun gospelCanticleUpdateAll(list: List<LHGospelCanticleTable?>?)

    @Delete(entity = LHGospelCanticleEntity::class)
    fun gospelCanticleDeleteAll(modelList: List<LHGospelCanticleTable?>?)

    @Insert(entity = LHIntercessionsEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhIntercessionsInsertAll(list: List<LHIntercession?>?)

    @Update(entity = LHIntercessionsEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhIntercessionsUpdateAll(list: List<LHIntercession?>?)

    @Delete(entity = LHIntercessionsEntity::class)
    fun lhIntercessionsDeleteAll(list: List<LHIntercession?>?)

    @Insert(entity = LHIntercessionsJoinEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhIntercessionsJoinInsertAll(list: List<LHIntercessionsJoin?>?)

    @Update(entity = LHIntercessionsJoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhIntercessionsJoinUpdateAll(list: List<LHIntercessionsJoin?>?)

    @Delete(entity = LHIntercessionsJoinEntity::class)
    fun lhIntercessionsJoinDeleteAll(list: List<LHIntercessionsJoin?>?)

    @Insert(entity = PrayerEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun prayerInsertAll(c: List<Prayer?>?)

    @Update(entity = PrayerEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun prayerUpdateAll(u: List<Prayer?>?)

    @Delete(entity = PrayerEntity::class)
    fun prayerDeleteAll(d: List<Prayer?>?)

    @Insert(entity = LHPrayerEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun lhPrayerInsertAll(c: List<LHPrayer?>?)

    @Update(entity = LHPrayerEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun lhPrayerUpdateAll(u: List<LHPrayer?>?)

    @Delete(entity = LHPrayerEntity::class)
    fun lhPrayerDeleteAll(d: List<LHPrayer?>?)

    //@Delete(entity = TodayEntity.class)
    @Query("DELETE FROM today WHERE todayDate LIKE '' || :lastYear || '____'")
    fun deleteOldEntries(lastYear: Int): Int

    companion object {
        const val todayByDate = "SELECT * FROM today AS t WHERE t.todayDate =:theDate"
        const val t = Constants.TODAY_TABLE

        //@Query(todayByDate)
        const val sqlSaint =
            "SELECT * FROM saint AS s WHERE s.theMonth=:theMonth AND theDay =:theDay"
    }
}