package org.deiverbum.app.model.crud

import org.deiverbum.app.data.database.dao.TodayDao

/**
 * Clase para manejar las operaciones de creación, lectura, actualización y borrado de datos.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class Crud {
    private var crudBibleBook: CrudBibleBook? = null
    private var crudBibleHomilyJoin: CrudBibleHomilyJoin? = null
    private var crudBibleHomilyTheme: CrudBibleHomilyTheme? = null
    private var crudBibleReading: CrudBibleReading? = null
    private var crudHomily: CrudHomily? = null
    private var crudKyrie: CrudKyrie? = null
    private var crudLHAntiphon: CrudLHAntiphon? = null
    private var crudLHEpigraph: CrudLHEpigraph? = null
    private var crudLHGospelCanticle: CrudLHGospelCanticle? = null
    private var crudLHHymn: CrudLHHymn? = null
    private var crudLHHymnJoin: CrudLHHymnJoin? = null
    private var crudLHIntercessions: CrudLHIntercessions? = null
    private var crudLHIntercessionsJoin: CrudLHIntercessionsJoin? = null
    private var crudLHInvitatory: CrudLHInvitatory? = null

    private var crudLHInvitatoryJoin: CrudLHInvitatoryJoin? = null
    private var crudLHKyrieJoin: CrudLHKyrieJoin? = null
    private var crudLHNightPrayer: CrudLHNightPrayer? = null

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
    private var crudLHVirginAntiphonJoin: CrudLHVirginAntiphonJoin? = null

    private var crudLiturgy: CrudLiturgy? = null
    private var crudLiturgyColor: CrudLiturgyColor? = null
    private var crudLiturgyTime: CrudLiturgyTime? = null

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
    private var crudVirginAntiphon: CrudVirginAntiphon? = null

    //public List<Today> today;
    var lastUpdate: String = ""
    var haveData = false
    /**
     * Este método maneja todas las respuestas Crud, sin importar de qué objeto sean.
     */
    fun doCrud(mTodayDao: TodayDao) {
        try {

            if (crudLiturgy != null) {
                val c = crudLiturgy!!.c
                val u = crudLiturgy!!.u
                val d = crudLiturgy!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.liturgyInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.liturgyUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.liturgyDeleteAll(d)
                }
            }
            if (crudSaint != null) {
                val c = crudSaint!!.c
                val u = crudSaint!!.u
                val d = crudSaint!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.saintInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.saintUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.saintDeleteAll(d)
                }
            }
            if (crudSaintLife != null) {
                val c = crudSaintLife!!.c
                val u = crudSaintLife!!.u
                val d = crudSaintLife!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.saintLifeInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.saintLifeUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.saintLifeDeleteAll(d)
                }
            }
            if (crudSaintShortLife != null) {
                val c = crudSaintShortLife!!.c
                val u = crudSaintShortLife!!.u
                val d = crudSaintShortLife!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.saintShortLifeInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.saintShortLifeUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.saintShortLifeDeleteAll(d)
                }
            }
            if (crudLHInvitatoryJoin != null) {
                val c = crudLHInvitatoryJoin!!.c
                val u = crudLHInvitatoryJoin!!.u
                val d = crudLHInvitatoryJoin!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhInvitatoryJoinInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhInvitatoryJoinUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhInvitatoryJoinDeleteAll(d)
                }
            }
            if (crudLHHymn != null) {
                val c = crudLHHymn!!.c
                val u = crudLHHymn!!.u
                val d = crudLHHymn!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhHymnInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhHymnUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhHymnDeleteAll(d)
                }
            }
            if (crudLHHymnJoin != null) {
                val c = crudLHHymnJoin!!.c
                val u = crudLHHymnJoin!!.u
                val d = crudLHHymnJoin!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhHymnJoinInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhHymnJoinUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhHymnJoinDeleteAll(d)
                }
            }
            if (crudLHOfficeVerse != null) {
                val c = crudLHOfficeVerse!!.c
                val u = crudLHOfficeVerse!!.u
                val d = crudLHOfficeVerse!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhOfficeVerseInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhOfficeVerseUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhOfficeVerseDeleteAll(d)
                }
            }
            if (crudLHOfficeVerseJoin != null) {
                val c = crudLHOfficeVerseJoin!!.c
                val u = crudLHOfficeVerseJoin!!.u
                val d = crudLHOfficeVerseJoin!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhOfficeVerseJoinInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhOfficeVerseJoinUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhOfficeVerseJoinDeleteAll(d)
                }
            }
            if (crudLHOfficeBiblical != null) {
                val c = crudLHOfficeBiblical!!.c
                val u = crudLHOfficeBiblical!!.u
                val d = crudLHOfficeBiblical!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhOfficeBiblicalInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhOfficeBiblicalUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhOfficeBiblicalDeleteAll(d)
                }
            }
            if (crudLHOfficeBiblicalJoin != null) {
                val c = crudLHOfficeBiblicalJoin!!.c
                val u = crudLHOfficeBiblicalJoin!!.u
                val d = crudLHOfficeBiblicalJoin!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhOfficeBiblicalJoinInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhOfficeBiblicalJoinUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhOfficeBiblicalJoinDeleteAll(d)
                }
            }
            if (crudLHResponsory != null) {
                val c = crudLHResponsory!!.c
                val u = crudLHResponsory!!.u
                val d = crudLHResponsory!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhResponsoryInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhResponsoryUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhResponsoryDeleteAll(d)
                }
            }
            if (crudLHOfficePatristic != null) {
                val c = crudLHOfficePatristic!!.c
                val u = crudLHOfficePatristic!!.u
                val d = crudLHOfficePatristic!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhOfficePatristicInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhOfficePatristicUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhOfficePatristicDeleteAll(d)
                }
            }
            if (crudLHOfficePatristicJoin != null) {
                val c = crudLHOfficePatristicJoin!!.c
                val u = crudLHOfficePatristicJoin!!.u
                val d = crudLHOfficePatristicJoin!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhOfficePatristicJoinInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhOfficePatristicJoinUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhOfficePatristicJoinDeleteAll(d)
                }
            }
            if (crudLHPsalmody != null) {
                val c = crudLHPsalmody!!.c
                val u = crudLHPsalmody!!.u
                val d = crudLHPsalmody!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhPsalmodyInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhPsalmodyUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhPsalmodyDeleteAll(d)
                }
            }
            if (crudLHPsalmodyJoin != null) {
                val c = crudLHPsalmodyJoin!!.c
                val u = crudLHPsalmodyJoin!!.u
                val d = crudLHPsalmodyJoin!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhPsalmodyJoinInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhPsalmodyJoinUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhPsalmodyJoinDeleteAll(d)
                }
            }
            if (crudLHAntiphon != null) {
                val d = crudLHAntiphon!!.d
                val c = crudLHAntiphon!!.c
                val u = crudLHAntiphon!!.u
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhAntiphonInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhAntiphonUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhAntiphonDeleteAll(d)
                }
            }
            if (crudLHTheme != null) {
                val d = crudLHTheme!!.d
                val c = crudLHTheme!!.c
                val u = crudLHTheme!!.u
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhThemeInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhThemeUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhThemeDeleteAll(d)
                }
            }
            if (crudLHEpigraph != null) {
                val d = crudLHEpigraph!!.d
                val c = crudLHEpigraph!!.c
                val u = crudLHEpigraph!!.u
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhEpigraphInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhEpigraphUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhEpigraphDeleteAll(d)
                }
            }
            if (crudLHPsalm != null) {
                val d = crudLHPsalm!!.d
                val c = crudLHPsalm!!.c
                val u = crudLHPsalm!!.u
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhPsalmInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhPsalmUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhPsalmDeleteAll(d)
                }
            }
            if (crudLHReadingShort != null) {
                val d = crudLHReadingShort!!.d
                val c = crudLHReadingShort!!.c
                val u = crudLHReadingShort!!.u
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhReadingShortInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhReadingShortUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhReadingShortDeleteAll(d)
                }
            }
            if (crudLHResponsoryShort != null) {
                val d = crudLHResponsoryShort!!.d
                val c = crudLHResponsoryShort!!.c
                val u = crudLHResponsoryShort!!.u
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhResponsoryShortInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhResponsoryShortUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhResponsoryShortDeleteAll(d)
                }
            }
            if (crudLHReadingShortJoin != null) {
                val d = crudLHReadingShortJoin!!.d
                val c = crudLHReadingShortJoin!!.c
                val u = crudLHReadingShortJoin!!.u
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhReadingShortJoinInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhReadingShortJoinUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhReadingShortJoinDeleteAll(d)
                }
            }
            if (crudPrayer != null) {
                val c = crudPrayer!!.c
                val u = crudPrayer!!.u
                val d = crudPrayer!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.prayerInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.prayerUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.prayerDeleteAll(d)
                }
            }
            if (crudLHPrayer != null) {
                val c = crudLHPrayer!!.c
                val u = crudLHPrayer!!.u
                val d = crudLHPrayer!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhPrayerInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhPrayerUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhPrayerDeleteAll(d)
                }
            }
            if (crudPater != null) {
                val c = crudPater!!.c
                val u = crudPater!!.u
                val d = crudPater!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.paterInsertAll(c)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.paterDeleteAll(d)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.paterUpdateAll(u)
                }
            }
            if (crudHomily != null) {
                val c = crudHomily!!.c
                val u = crudHomily!!.u
                val d = crudHomily!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.homilyInsertAll(c)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.homilyDeleteAll(d)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.homilyUpdateAll(u)
                }
            }
            if (crudLiturgyHomilyJoin != null) {
                val c = crudLiturgyHomilyJoin!!.c
                val u = crudLiturgyHomilyJoin!!.u
                val d = crudLiturgyHomilyJoin!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.liturgyHomilyJoinInsertAll(c)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.liturgyHomilyJoinDeleteAll(d)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.liturgyHomilyJoinUpdateAll(u)
                }
            }
            if (crudLiturgySaintJoin != null) {
                val c = crudLiturgySaintJoin!!.c
                val u = crudLiturgySaintJoin!!.u
                val d = crudLiturgySaintJoin!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.liturgySaintJoinInsertAll(c)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.liturgySaintJoinDeleteAll(d)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.liturgySaintJoinUpdateAll(u)
                }
            }
            if (crudBibleReading != null) {
                val c = crudBibleReading!!.c
                val u = crudBibleReading!!.u
                val d = crudBibleReading!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.bibleReadingInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.bibleReadingUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.bibleReadingDeleteAll(d)
                }
            }
            if (crudMassReading != null) {
                val c = crudMassReading!!.c
                val u = crudMassReading!!.u
                val d = crudMassReading!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.massReadingInsertAll(c)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.massReadingDeleteAll(d)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.massReadingUpdateAll(u)
                }
            }
            if (crudBibleHomilyJoin != null) {
                val c = crudBibleHomilyJoin!!.c
                val u = crudBibleHomilyJoin!!.u
                val d = crudBibleHomilyJoin!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.bibleHomilyJoinInsertAll(c)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.bibleHomilyJoinDeleteAll(d)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.bibleHomilyJoinUpdateAll(u)
                }
            }
            if (crudLHGospelCanticle != null) {
                val c = crudLHGospelCanticle!!.c
                val u = crudLHGospelCanticle!!.u
                val d = crudLHGospelCanticle!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.gospelCanticleInsertAll(c)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.gospelCanticleDeleteAll(d)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.gospelCanticleUpdateAll(u)
                }
            }
            if (crudLHIntercessions != null) {
                val c = crudLHIntercessions!!.c
                val u = crudLHIntercessions!!.u
                val d = crudLHIntercessions!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhIntercessionsInsertAll(c)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhIntercessionsDeleteAll(d)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhIntercessionsUpdateAll(u)
                }
            }
            if (crudLHIntercessionsJoin != null) {
                val c = crudLHIntercessionsJoin!!.c
                val u = crudLHIntercessionsJoin!!.u
                val d = crudLHIntercessionsJoin!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhIntercessionsJoinInsertAll(c)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhIntercessionsJoinDeleteAll(d)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhIntercessionsJoinUpdateAll(u)
                }
            }
            if (crudMassReadingJoin != null) {
                val c = crudMassReadingJoin!!.c
                val u = crudMassReadingJoin!!.u
                val d = crudMassReadingJoin!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.massReadingJoinInsertAll(c)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.massReadingJoinDeleteAll(d)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.massReadingJoinUpdateAll(u)
                }
            }
            if (crudBibleHomilyTheme != null) {
                val c = crudBibleHomilyTheme!!.c
                val u = crudBibleHomilyTheme!!.u
                val d = crudBibleHomilyTheme!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.bibleHomilyThemeInsertAll(c)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.bibleHomilyThemeDeleteAll(d)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.bibleHomilyThemeUpdateAll(u)
                }
            }
            if (crudBibleHomilyJoin != null) {
                val c = crudBibleHomilyJoin!!.c
                val u = crudBibleHomilyJoin!!.u
                val d = crudBibleHomilyJoin!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.bibleHomilyJoinInsertAll(c)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.bibleHomilyJoinDeleteAll(d)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.bibleHomilyJoinUpdateAll(u)
                }
            }
            if (crudPaterOpus != null) {
                val c = crudPaterOpus!!.c
                val u = crudPaterOpus!!.u
                val d = crudPaterOpus!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.paterOpusInsertAll(c)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.paterOpusDeleteAll(d)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.paterOpusUpdateAll(u)
                }
            }

            if (crudToday != null) {
                val c = crudToday!!.c
                val u = crudToday!!.u
                val d = crudToday!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.todayInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.todayUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.todayDeleteAll(d)
                }
            }

            if (crudBibleBook != null) {
                val c = crudBibleBook!!.c
                val u = crudBibleBook!!.u
                val d = crudBibleBook!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.bibleBookInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.bibleBookUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.bibleBookDeleteAll(d)
                }
            }

            if (crudKyrie != null) {
                val c = crudKyrie!!.c
                val u = crudKyrie!!.u
                val d = crudKyrie!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.kyrieInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.kyrieUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.kyrieDeleteAll(d)
                }
            }


            if (crudLHInvitatory != null) {
                val c = crudLHInvitatory!!.c
                val u = crudLHInvitatory!!.u
                val d = crudLHInvitatory!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhInvitatoryInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhInvitatoryUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhInvitatoryDeleteAll(d)
                }
            }

            if (crudLHKyrieJoin != null) {
                val c = crudLHKyrieJoin!!.c
                val u = crudLHKyrieJoin!!.u
                val d = crudLHKyrieJoin!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhKyrieJoinInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhKyrieJoinUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhKyrieJoinDeleteAll(d)
                }
            }


            if (crudLHNightPrayer != null) {
                val c = crudLHNightPrayer!!.c
                val u = crudLHNightPrayer!!.u
                val d = crudLHNightPrayer!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhNightPrayerInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhNightPrayerUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhNightPrayerDeleteAll(d)
                }
            }


            if (crudLHVirginAntiphonJoin != null) {
                val c = crudLHVirginAntiphonJoin!!.c
                val u = crudLHVirginAntiphonJoin!!.u
                val d = crudLHVirginAntiphonJoin!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.lhVirginAntiphonJoinInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.lhVirginAntiphonJoinUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.lhVirginAntiphonJoinDeleteAll(d)
                }
            }


            if (crudLiturgyColor != null) {
                val c = crudLiturgyColor!!.c
                val u = crudLiturgyColor!!.u
                val d = crudLiturgyColor!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.liturgyColorInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.liturgyColorUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.liturgyColorDeleteAll(d)
                }
            }


            if (crudLiturgyTime != null) {
                val c = crudLiturgyTime!!.c
                val u = crudLiturgyTime!!.u
                val d = crudLiturgyTime!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.liturgyTimeInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.liturgyTimeUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.liturgyTimeDeleteAll(d)
                }
            }


            if (crudVirginAntiphon != null) {
                val c = crudVirginAntiphon!!.c
                val u = crudVirginAntiphon!!.u
                val d = crudVirginAntiphon!!.d
                if (!c.isNullOrEmpty()) {
                    mTodayDao.virginAntiphonInsertAll(c)
                }
                if (!u.isNullOrEmpty()) {
                    mTodayDao.virginAntiphonUpdateAll(u)
                }
                if (!d.isNullOrEmpty()) {
                    mTodayDao.virginAntiphonDeleteAll(d)
                }


            }
        } catch (e: Exception) {
            //Log.d("Crud", e.message!!)
        }
    }
}