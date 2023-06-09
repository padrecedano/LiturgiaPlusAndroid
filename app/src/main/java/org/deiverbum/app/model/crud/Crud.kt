package org.deiverbum.app.model.crud

import android.util.Log
import org.deiverbum.app.data.database.dao.TodayDao

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class Crud {
    private var crudBibleHomilyJoin: CrudBibleHomilyJoin? = null
    private var crudBibleHomilyTheme: CrudBibleHomilyTheme? = null
    private var crudBibleReading: CrudBibleReading? = null
    private var crudHomily: CrudHomily? = null
    private var crudLHAntiphon: CrudLHAntiphon? = null
    private var crudLHEpigraph: CrudLHEpigraph? = null
    private var crudLHGospelCanticle: CrudLHGospelCanticle? = null
    private var crudLHHymn: CrudLHHymn? = null
    private var crudLHHymnJoin: CrudLHHymnJoin? = null
    private var crudLHIntercessions: CrudLHIntercessions? = null
    private var crudLHIntercessionsJoin: CrudLHIntercessionsJoin? = null
    private var crudLHInvitatoryJoin: CrudLHInvitatoryJoin? = null
    private var crudLHOfficeBiblical: CrudLHOfficeBiblical? = null
    private var crudLHOfficeBiblicalJoin: CrudLHOfficeBiblicalJoin? = null
    private var crudLHOfficePatristic: CrudLHOfficePatristic? = null
    private var crudLHOfficePatristicJoin: CrudLHOfficePatristicJoin? = null
    private var crudLHOfficeVerse: CrudLHOfficeVerse? = null
    private var crudLHOfficeVerseJoin: CrudLHOfficeVerseJoin? = null
    private var crudLHPrayer: CrudLHPrayer? = null
    private var crudLHPsalm: CrudLHPsalm? = null
    private var crudLHPsalmody: CrudLHPsalmody? = null
    private var crudLHPsalmodyJoin: CrudLHPsalmodyJoin? = null
    private var crudLHReadingShort: CrudLHReadingShort? = null
    private var crudLHReadingShortJoin: CrudLHReadingShortJoin? = null
    private var crudLHResponsory: CrudLHResponsory? = null
    private var crudLHResponsoryShort: CrudLHResponsoryShort? = null
    private var crudLHTheme: CrudLHTheme? = null
    private var crudLiturgy: CrudLiturgy? = null
    private var crudLiturgyHomilyJoin: CrudLiturgiaHomiliaJoin? = null
    private var crudLiturgySaintJoin: CrudLiturgySaintJoin? = null
    private var crudMassReading: CrudMassReading? = null
    private var crudMassReadingJoin: CrudMassReadingJoin? = null
    private var crudPater: CrudPater? = null
    private var crudPaterOpus: CrudPaterOpus? = null
    private var crudPrayer: CrudPrayer? = null
    private var crudSaint: CrudSaint? = null
    private var crudSaintLife: CrudSaintLife? = null
    private var crudSaintShortLife: CrudSaintShortLife? = null
    private var crudToday: CrudToday? = null

    //public List<Today> today;
    @JvmField
    var lastUpdate: String? = null
    @JvmField
    var haveData = false
    fun doCrud(mTodayDao: TodayDao) {
        try {
            if (crudLiturgy != null) {
                val c = crudLiturgy!!.c
                val u = crudLiturgy!!.u
                val d = crudLiturgy!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.liturgyInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.liturgyUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.liturgyDeleteAll(d)
                }
            }
            if (crudSaint != null) {
                val c = crudSaint!!.c
                val u = crudSaint!!.u
                val d = crudSaint!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.saintInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.saintUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.saintDeleteAll(d)
                }
            }
            if (crudSaintLife != null) {
                val c = crudSaintLife!!.c
                val u = crudSaintLife!!.u
                val d = crudSaintLife!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.saintLifeInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.saintLifeUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.saintLifeDeleteAll(d)
                }
            }
            if (crudSaintShortLife != null) {
                val c = crudSaintShortLife!!.c
                val u = crudSaintShortLife!!.u
                val d = crudSaintShortLife!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.saintShortLifeInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.saintShortLifeUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.saintShortLifeDeleteAll(d)
                }
            }
            if (crudLHInvitatoryJoin != null) {
                val c = crudLHInvitatoryJoin!!.c
                val u = crudLHInvitatoryJoin!!.u
                val d = crudLHInvitatoryJoin!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhInvitatoryJoinInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhInvitatoryJoinUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhInvitatoryJoinDeleteAll(d)
                }
            }
            if (crudLHHymn != null) {
                val c = crudLHHymn!!.c
                val u = crudLHHymn!!.u
                val d = crudLHHymn!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhHymnInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhHymnUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhHymnDeleteAll(d)
                }
            }
            if (crudLHHymnJoin != null) {
                val c = crudLHHymnJoin!!.c
                val u = crudLHHymnJoin!!.u
                val d = crudLHHymnJoin!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhHymnJoinInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhHymnJoinUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhHymnJoinDeleteAll(d)
                }
            }
            if (crudLHOfficeVerse != null) {
                val c = crudLHOfficeVerse!!.c
                val u = crudLHOfficeVerse!!.u
                val d = crudLHOfficeVerse!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhOfficeVerseInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhOfficeVerseUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhOfficeVerseDeleteAll(d)
                }
            }
            if (crudLHOfficeVerseJoin != null) {
                val c = crudLHOfficeVerseJoin!!.c
                val u = crudLHOfficeVerseJoin!!.u
                val d = crudLHOfficeVerseJoin!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhOfficeVerseJoinInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhOfficeVerseJoinUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhOfficeVerseJoinDeleteAll(d)
                }
            }
            if (crudLHOfficeBiblical != null) {
                val c = crudLHOfficeBiblical!!.c
                val u = crudLHOfficeBiblical!!.u
                val d = crudLHOfficeBiblical!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhOfficeBiblicalInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhOfficeBiblicalUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhOfficeBiblicalDeleteAll(d)
                }
            }
            if (crudLHOfficeBiblicalJoin != null) {
                val c = crudLHOfficeBiblicalJoin!!.c
                val u = crudLHOfficeBiblicalJoin!!.u
                val d = crudLHOfficeBiblicalJoin!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhOfficeBiblicalJoinInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhOfficeBiblicalJoinUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhOfficeBiblicalJoinDeleteAll(d)
                }
            }
            if (crudLHResponsory != null) {
                val c = crudLHResponsory!!.c
                val u = crudLHResponsory!!.u
                val d = crudLHResponsory!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhResponsoryInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhResponsoryUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhResponsoryDeleteAll(d)
                }
            }
            if (crudLHOfficePatristic != null) {
                val c = crudLHOfficePatristic!!.c
                val u = crudLHOfficePatristic!!.u
                val d = crudLHOfficePatristic!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhOfficePatristicInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhOfficePatristicUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhOfficePatristicDeleteAll(d)
                }
            }
            if (crudLHOfficePatristicJoin != null) {
                val c = crudLHOfficePatristicJoin!!.c
                val u = crudLHOfficePatristicJoin!!.u
                val d = crudLHOfficePatristicJoin!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhOfficePatristicJoinInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhOfficePatristicJoinUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhOfficePatristicJoinDeleteAll(d)
                }
            }
            if (crudLHPsalmody != null) {
                val c = crudLHPsalmody!!.c
                val u = crudLHPsalmody!!.u
                val d = crudLHPsalmody!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhPsalmodyInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhPsalmodyUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhPsalmodyDeleteAll(d)
                }
            }
            if (crudLHPsalmodyJoin != null) {
                val c = crudLHPsalmodyJoin!!.c
                val u = crudLHPsalmodyJoin!!.u
                val d = crudLHPsalmodyJoin!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhPsalmodyJoinInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhPsalmodyJoinUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhPsalmodyJoinDeleteAll(d)
                }
            }
            if (crudLHAntiphon != null) {
                val d = crudLHAntiphon!!.d
                val c = crudLHAntiphon!!.c
                val u = crudLHAntiphon!!.u
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhAntiphonInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhAntiphonUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhAntiphonDeleteAll(d)
                }
            }
            if (crudLHTheme != null) {
                val d = crudLHTheme!!.d
                val c = crudLHTheme!!.c
                val u = crudLHTheme!!.u
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhThemeInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhThemeUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhThemeDeleteAll(d)
                }
            }
            if (crudLHEpigraph != null) {
                val d = crudLHEpigraph!!.d
                val c = crudLHEpigraph!!.c
                val u = crudLHEpigraph!!.u
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhEpigraphInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhEpigraphUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhEpigraphDeleteAll(d)
                }
            }
            if (crudLHPsalm != null) {
                val d = crudLHPsalm!!.d
                val c = crudLHPsalm!!.c
                val u = crudLHPsalm!!.u
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhPsalmInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhPsalmUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhPsalmDeleteAll(d)
                }
            }
            if (crudLHReadingShort != null) {
                val d = crudLHReadingShort!!.d
                val c = crudLHReadingShort!!.c
                val u = crudLHReadingShort!!.u
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhReadingShortInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhReadingShortUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhReadingShortDeleteAll(d)
                }
            }
            if (crudLHResponsoryShort != null) {
                val d = crudLHResponsoryShort!!.d
                val c = crudLHResponsoryShort!!.c
                val u = crudLHResponsoryShort!!.u
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhResponsoryShortInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhResponsoryShortUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhResponsoryShortDeleteAll(d)
                }
            }
            if (crudLHReadingShortJoin != null) {
                val d = crudLHReadingShortJoin!!.d
                val c = crudLHReadingShortJoin!!.c
                val u = crudLHReadingShortJoin!!.u
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhReadingShortJoinInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhReadingShortJoinUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhReadingShortJoinDeleteAll(d)
                }
            }
            if (crudPrayer != null) {
                val c = crudPrayer!!.c
                val u = crudPrayer!!.u
                val d = crudPrayer!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.prayerInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.prayerUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.prayerDeleteAll(d)
                }
            }
            if (crudLHPrayer != null) {
                val c = crudLHPrayer!!.c
                val u = crudLHPrayer!!.u
                val d = crudLHPrayer!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhPrayerInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhPrayerUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhPrayerDeleteAll(d)
                }
            }
            if (crudPater != null) {
                val c = crudPater!!.c
                val u = crudPater!!.u
                val d = crudPater!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.paterInsertAll(c)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.paterDeleteAll(d)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.paterUpdateAll(u)
                }
            }
            if (crudHomily != null) {
                val c = crudHomily!!.c
                val u = crudHomily!!.u
                val d = crudHomily!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.homilyInsertAll(c)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.homilyDeleteAll(d)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.homilyUpdateAll(u)
                }
            }
            if (crudLiturgyHomilyJoin != null) {
                val c = crudLiturgyHomilyJoin!!.c
                val u = crudLiturgyHomilyJoin!!.u
                val d = crudLiturgyHomilyJoin!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.liturgyHomilyJoinInsertAll(c)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.liturgyHomilyJoinDeleteAll(d)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.liturgyHomilyJoinUpdateAll(u)
                }
            }
            if (crudLiturgySaintJoin != null) {
                val c = crudLiturgySaintJoin!!.c
                val u = crudLiturgySaintJoin!!.u
                val d = crudLiturgySaintJoin!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.liturgySaintJoinInsertAll(c)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.liturgySaintJoinDeleteAll(d)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.liturgySaintJoinUpdateAll(u)
                }
            }
            if (crudBibleReading != null) {
                val c = crudBibleReading!!.c
                val u = crudBibleReading!!.u
                val d = crudBibleReading!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.bibleReadingInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.bibleReadingUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.bibleReadingDeleteAll(d)
                }
            }
            if (crudMassReading != null) {
                val c = crudMassReading!!.c
                val u = crudMassReading!!.u
                val d = crudMassReading!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.massReadingInsertAll(c)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.massReadingDeleteAll(d)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.massReadingUpdateAll(u)
                }
            }
            if (crudBibleHomilyJoin != null) {
                val c = crudBibleHomilyJoin!!.c
                val u = crudBibleHomilyJoin!!.u
                val d = crudBibleHomilyJoin!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.bibleHomilyJoinInsertAll(c)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.bibleHomilyJoinDeleteAll(d)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.bibleHomilyJoinUpdateAll(u)
                }
            }
            if (crudLHGospelCanticle != null) {
                val c = crudLHGospelCanticle!!.c
                val u = crudLHGospelCanticle!!.u
                val d = crudLHGospelCanticle!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.gospelCanticleInsertAll(c)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.gospelCanticleDeleteAll(d)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.gospelCanticleUpdateAll(u)
                }
            }
            if (crudLHIntercessions != null) {
                val c = crudLHIntercessions!!.c
                val u = crudLHIntercessions!!.u
                val d = crudLHIntercessions!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhIntercessionsInsertAll(c)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhIntercessionsDeleteAll(d)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhIntercessionsUpdateAll(u)
                }
            }
            if (crudLHIntercessionsJoin != null) {
                val c = crudLHIntercessionsJoin!!.c
                val u = crudLHIntercessionsJoin!!.u
                val d = crudLHIntercessionsJoin!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.lhIntercessionsJoinInsertAll(c)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.lhIntercessionsJoinDeleteAll(d)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.lhIntercessionsJoinUpdateAll(u)
                }
            }
            if (crudMassReadingJoin != null) {
                val c = crudMassReadingJoin!!.c
                val u = crudMassReadingJoin!!.u
                val d = crudMassReadingJoin!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.massReadingJoinInsertAll(c)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.massReadingJoinDeleteAll(d)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.massReadingJoinUpdateAll(u)
                }
            }
            if (crudBibleHomilyTheme != null) {
                val c = crudBibleHomilyTheme!!.c
                val u = crudBibleHomilyTheme!!.u
                val d = crudBibleHomilyTheme!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.bibleHomilyThemeInsertAll(c)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.bibleHomilyThemeDeleteAll(d)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.bibleHomilyThemeUpdateAll(u)
                }
            }
            if (crudBibleHomilyJoin != null) {
                val c = crudBibleHomilyJoin!!.c
                val u = crudBibleHomilyJoin!!.u
                val d = crudBibleHomilyJoin!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.bibleHomilyJoinInsertAll(c)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.bibleHomilyJoinDeleteAll(d)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.bibleHomilyJoinUpdateAll(u)
                }
            }
            if (crudPaterOpus != null) {
                val c = crudPaterOpus!!.c
                val u = crudPaterOpus!!.u
                val d = crudPaterOpus!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.paterOpusInsertAll(c)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.paterOpusDeleteAll(d)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.paterOpusUpdateAll(u)
                }
            }
            if (crudToday != null) {
                val c = crudToday!!.c
                val u = crudToday!!.u
                val d = crudToday!!.d
                if (c != null && c.isNotEmpty()) {
                    mTodayDao.todayInsertAll(c)
                }
                if (u != null && u.isNotEmpty()) {
                    mTodayDao.todayUpdateAll(u)
                }
                if (d != null && d.isNotEmpty()) {
                    mTodayDao.todayDeleteAll(d)
                }
            }
        } catch (e: Exception) {
            Log.d("Crud", e.message!!)
        }
    }
}