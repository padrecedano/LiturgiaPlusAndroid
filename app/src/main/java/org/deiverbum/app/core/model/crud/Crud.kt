package org.deiverbum.app.core.model.data.crud

import android.util.Log
import org.deiverbum.app.core.database.dao.TodayDao
import org.deiverbum.app.core.model.breviarium.LHPsalmodyJoin

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
    private var crudLHPsalmJoin: CrudLHPsalmJoin? = null
    private var crudLHPsalmJoinList: CrudLHPsalmJoinList? = null
    private var crudLHAntiphonJoinList: CrudLHAntiphonJoinList? = null
    private var crudLHAntiphonJoin: CrudLHAntiphonJoin? = null

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
    private var crudUniversalis: CrudUniversalis? = null
    private var crudVirginAntiphon: CrudVirginAntiphon? = null

    //public List<Today> today;
    var lastUpdate: String = ""
    var haveData = false

    //lateinit var crud : List<T>
    //val crud: List<T> = emptyList()
    //val create: List<LHPsalmodyJoin> = emptyList()
    //val createe: List<T> = emptyList()
    val lhPsalmodyJoin: List<LHPsalmodyJoin> = emptyList()

    /**
     * Este método maneja todas las respuestas Crud, sin importar de qué objeto sean.
     */
    fun doCrud(todayDao: TodayDao) {
        try {

            if (crudLiturgy != null) {
                val cr = crudLiturgy!!.c
                val up = crudLiturgy!!.u
                val de = crudLiturgy!!.d
                if (!cr.isNullOrEmpty()) {
                    todayDao.liturgyInsertAll(cr)
                }
                if (!up.isNullOrEmpty()) {
                    todayDao.liturgyUpdateAll(up)
                }
                if (!de.isNullOrEmpty()) {
                    todayDao.liturgyDeleteAll(de)
                }
            }

            if (crudLiturgyTime != null) {
                val cr = crudLiturgyTime!!.c
                val up = crudLiturgyTime!!.u
                val de = crudLiturgyTime!!.d
                if (!cr.isNullOrEmpty()) {
                    todayDao.liturgyTimeInsertAll(cr)
                }
                if (!up.isNullOrEmpty()) {
                    todayDao.liturgyTimeUpdateAll(up)
                }
                if (!de.isNullOrEmpty()) {
                    todayDao.liturgyTimeDeleteAll(de)
                }
            }

            if (crudLiturgyColor != null) {
                val cr = crudLiturgyColor!!.c
                val up = crudLiturgyColor!!.u
                val de = crudLiturgyColor!!.d
                if (!cr.isNullOrEmpty()) {
                    todayDao.liturgyColorInsertAll(cr)
                }
                if (!up.isNullOrEmpty()) {
                    todayDao.liturgyColorUpdateAll(up)
                }
                if (!de.isNullOrEmpty()) {
                    todayDao.liturgyColorDeleteAll(de)
                }
            }

            if (crudMassReadingJoin != null) {
                val cr = crudMassReadingJoin!!.c
                val up = crudMassReadingJoin!!.u
                val de = crudMassReadingJoin!!.d
                if (!cr.isNullOrEmpty()) {
                    todayDao.massReadingJoinInsertAll(cr)
                }
                if (!de.isNullOrEmpty()) {
                    todayDao.massReadingJoinDeleteAll(de)
                }
                if (!up.isNullOrEmpty()) {
                    todayDao.massReadingJoinUpdateAll(up)
                }
            }

            if (crudBibleBook != null) {
                val cr = crudBibleBook!!.c
                val up = crudBibleBook!!.u
                val de = crudBibleBook!!.d
                if (!cr.isNullOrEmpty()) {
                    todayDao.bibleBookInsertAll(cr)
                }
                if (!up.isNullOrEmpty()) {
                    todayDao.bibleBookUpdateAll(up)
                }
                if (!de.isNullOrEmpty()) {
                    todayDao.bibleBookDeleteAll(de)
                }
            }

            if (crudKyrie != null) {
                val cr = crudKyrie!!.c
                val up = crudKyrie!!.u
                val de = crudKyrie!!.d
                if (!cr.isNullOrEmpty()) {
                    todayDao.kyrieInsertAll(cr)
                }
                if (!up.isNullOrEmpty()) {
                    todayDao.kyrieUpdateAll(up)
                }
                if (!de.isNullOrEmpty()) {
                    todayDao.kyrieDeleteAll(de)
                }
            }

            if (crudVirginAntiphon != null) {
                val cr = crudVirginAntiphon!!.c
                val up = crudVirginAntiphon!!.u
                val de = crudVirginAntiphon!!.d
                if (!cr.isNullOrEmpty()) {
                    todayDao.virginAntiphonInsertAll(cr)
                }
                if (!up.isNullOrEmpty()) {
                    todayDao.virginAntiphonUpdateAll(up)
                }
                if (!de.isNullOrEmpty()) {
                    todayDao.virginAntiphonDeleteAll(de)
                }
            }

                if (crudSaint != null) {
                    val cr = crudSaint!!.c
                    val up = crudSaint!!.u
                    val de = crudSaint!!.d
                    if (!crudSaint!!.c.isNullOrEmpty()) {
                        todayDao.saintInsertAll(cr!!)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.saintUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.saintDeleteAll(de)
                    }
                }
                if (crudSaintLife != null) {
                    val cr = crudSaintLife!!.c
                    val up = crudSaintLife!!.u
                    val de = crudSaintLife!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.saintLifeInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.saintLifeUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.saintLifeDeleteAll(de)
                    }
                }
                if (crudSaintShortLife != null) {
                    val cr = crudSaintShortLife!!.c
                    val up = crudSaintShortLife!!.u
                    val de = crudSaintShortLife!!.d
                    if (!crudSaintShortLife!!.c.isNullOrEmpty()) {
                        todayDao.saintShortLifeInsertAll(crudSaintShortLife!!.c!!)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.saintShortLifeUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.saintShortLifeDeleteAll(de)
                    }
                }

                if (crudLHHymn != null) {
                    val cr = crudLHHymn!!.c
                    val up = crudLHHymn!!.u
                    val de = crudLHHymn!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.lhHymnInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhHymnUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhHymnDeleteAll(de)
                    }
                }
                if (crudLHHymnJoin != null) {
                    val cr = crudLHHymnJoin!!.c
                    val up = crudLHHymnJoin!!.u
                    val de = crudLHHymnJoin!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.lhHymnJoinInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhHymnJoinUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhHymnJoinDeleteAll(de)
                    }
                }
                if (crudLHOfficeVerse != null) {
                    val cr = crudLHOfficeVerse!!.c
                    val up = crudLHOfficeVerse!!.u
                    val de = crudLHOfficeVerse!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.lhOfficeVerseInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhOfficeVerseUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhOfficeVerseDeleteAll(de)
                    }
                }
                if (crudLHOfficeVerseJoin != null) {
                    val cr = crudLHOfficeVerseJoin!!.c
                    val up = crudLHOfficeVerseJoin!!.u
                    val de = crudLHOfficeVerseJoin!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.lhOfficeVerseJoinInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhOfficeVerseJoinUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhOfficeVerseJoinDeleteAll(de)
                    }
                }

                if (crudBibleReading != null) {
                    val cr = crudBibleReading!!.c
                    val up = crudBibleReading!!.u
                    val de = crudBibleReading!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.bibleReadingInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.bibleReadingUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.bibleReadingDeleteAll(de)
                    }
                }
                if (crudMassReading != null) {
                    val cr = crudMassReading!!.c
                    val up = crudMassReading!!.u
                    val de = crudMassReading!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.massReadingInsertAll(cr)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.massReadingDeleteAll(de)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.massReadingUpdateAll(up)
                    }
                }

                if (crudLHOfficeBiblical != null) {
                    val cr = crudLHOfficeBiblical!!.c
                    val up = crudLHOfficeBiblical!!.u
                    val de = crudLHOfficeBiblical!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.lhOfficeBiblicalInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhOfficeBiblicalUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhOfficeBiblicalDeleteAll(de)
                    }
                }
                if (crudLHOfficeBiblicalJoin != null) {
                    val cr = crudLHOfficeBiblicalJoin!!.c
                    val up = crudLHOfficeBiblicalJoin!!.u
                    val de = crudLHOfficeBiblicalJoin!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.lhOfficeBiblicalJoinInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhOfficeBiblicalJoinUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhOfficeBiblicalJoinDeleteAll(de)
                    }
                }
                if (crudLHResponsory != null) {
                    val cr = crudLHResponsory!!.c
                    val up = crudLHResponsory!!.u
                    val de = crudLHResponsory!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.lhResponsoryInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhResponsoryUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhResponsoryDeleteAll(de)
                    }
                }

                if (crudPater != null) {
                    val cr = crudPater!!.c
                    val up = crudPater!!.u
                    val de = crudPater!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.paterInsertAll(cr)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.paterDeleteAll(de)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.paterUpdateAll(up)
                    }
                }

                if (crudPaterOpus != null) {
                    val cr = crudPaterOpus!!.c
                    val up = crudPaterOpus!!.u
                    val de = crudPaterOpus!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.paterOpusInsertAll(cr)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.paterOpusDeleteAll(de)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.paterOpusUpdateAll(up)
                    }
                }

                if (crudHomily != null) {
                    val cr = crudHomily!!.c
                    val up = crudHomily!!.u
                    val de = crudHomily!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.homilyInsertAll(cr)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.homilyDeleteAll(de)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.homilyUpdateAll(up)
                    }
                }

                if (crudLHOfficePatristic != null) {
                    val cr = crudLHOfficePatristic!!.c
                    val up = crudLHOfficePatristic!!.u
                    val de = crudLHOfficePatristic!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.lhOfficePatristicInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhOfficePatristicUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhOfficePatristicDeleteAll(de)
                    }
                }
                if (crudLHOfficePatristicJoin != null) {
                    val cr = crudLHOfficePatristicJoin!!.c
                    val up = crudLHOfficePatristicJoin!!.u
                    val de = crudLHOfficePatristicJoin!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.lhOfficePatristicJoinInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhOfficePatristicJoinUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhOfficePatristicJoinDeleteAll(de)
                    }
                }


                if (crudLHAntiphon != null) {
                    val de = crudLHAntiphon!!.d
                    val cr = crudLHAntiphon!!.c
                    val up = crudLHAntiphon!!.u
                    if (!cr.isNullOrEmpty()) {
                        todayDao.lhAntiphonInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhAntiphonUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhAntiphonDeleteAll(de)
                    }
                }
                if (crudLHTheme != null) {
                    val de = crudLHTheme!!.d
                    val cr = crudLHTheme!!.c
                    val up = crudLHTheme!!.u
                    if (!cr.isNullOrEmpty()) {
                        todayDao.lhThemeInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhThemeUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhThemeDeleteAll(de)
                    }
                }
                if (crudLHEpigraph != null) {
                    val de = crudLHEpigraph!!.d
                    val cr = crudLHEpigraph!!.c
                    val up = crudLHEpigraph!!.u
                    if (!crudLHEpigraph!!.d.isNullOrEmpty()) {
                        todayDao.lhEpigraphInsertAll(crudLHEpigraph!!.c!!)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhEpigraphUpdateAll(crudLHEpigraph!!.u!!)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhEpigraphDeleteAll(crudLHEpigraph!!.d!!)
                    }
                }
                if (crudLHPsalm != null) {
                    val de = crudLHPsalm!!.d
                    val cr = crudLHPsalm!!.c
                    val up = crudLHPsalm!!.u
                    if (!cr.isNullOrEmpty()) {
                        todayDao.lhPsalmInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhPsalmUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhPsalmDeleteAll(de)
                    }
                }

            if (crudLHPsalmodyJoin != null) {
                val cr = crudLHPsalmodyJoin!!.c
                val up = crudLHPsalmodyJoin!!.u
                val de = crudLHPsalmodyJoin!!.d
                if (!cr.isNullOrEmpty()) {
                    todayDao.lhPsalmodyJoinInsertAll(cr)
                }
                if (!up.isNullOrEmpty()) {
                    todayDao.lhPsalmodyJoinUpdateAll(up)
                }
                if (!de.isNullOrEmpty()) {
                    todayDao.lhPsalmodyJoinDeleteAll(de)
                }
            }

            if (crudLHAntiphonJoin != null) {
                val cr = crudLHAntiphonJoin!!.c
                val up = crudLHAntiphonJoin!!.u
                val de = crudLHAntiphonJoin!!.d
                if (!cr.isNullOrEmpty()) {
                    todayDao.lhAntiphonJoinInsertAll(cr)
                }
                /*if (!up.isNullOrEmpty()) {
                    todayDao.updateLHAntiphonJoinAll(up)
                }
                if (!de.isNullOrEmpty()) {
                    todayDao.deleteLHAntiphonJoinAll(de)
                }*/
            }

            if (crudLHPsalmJoin != null) {
                val cr = crudLHPsalmJoin!!.c
                val up = crudLHPsalmJoin!!.u
                val de = crudLHPsalmJoin!!.d
                if (!cr.isNullOrEmpty()) {
                    todayDao.lhPsalmJoinInsertAll(cr)
                }
                /*if (!up.isNullOrEmpty()) {
                    val t = todayDao.lhPsalmJoinUpdateAll(up)
                    println("kkk: $t")
                }
                if (!de.isNullOrEmpty()) {
                    val t = todayDao.lhPsalmJoinDeleteAll(de)
                    println("kkk: $t")
                }*/
            }

            if (crudLHPsalmJoinList != null) {
                if (!crudLHPsalmJoinList!!.u.isNullOrEmpty()) {
                    todayDao.updateLHPsalmJoinAll(crudLHPsalmJoinList!!.u!!)
                }
                if (!crudLHPsalmJoinList!!.d.isNullOrEmpty()) {
                    todayDao.deleteLHPsalmJoinAll(crudLHPsalmJoinList!!.d!!)
                }
            }

            if (crudLHAntiphonJoinList != null) {
                if (!crudLHAntiphonJoinList!!.u.isNullOrEmpty()) {
                    todayDao.updateLHAntiphonJoinAll(crudLHAntiphonJoinList!!.u!!)
                }
                if (!crudLHAntiphonJoinList!!.d.isNullOrEmpty()) {
                    todayDao.deleteLHAntiphonJoinAll(crudLHAntiphonJoinList!!.d!!)
                }
            }

            if (crudLHInvitatoryJoin != null) {
                val cr = crudLHInvitatoryJoin!!.c
                val up = crudLHInvitatoryJoin!!.u
                val de = crudLHInvitatoryJoin!!.d
                if (!cr.isNullOrEmpty()) {
                    todayDao.lhInvitatoryJoinInsertAll(cr)
                }
                if (!up.isNullOrEmpty()) {
                    todayDao.lhInvitatoryJoinUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhInvitatoryJoinDeleteAll(de)
                    }
                }

                if (crudLHReadingShort != null) {
                    val de = crudLHReadingShort!!.d
                    val cr = crudLHReadingShort!!.c
                    val up = crudLHReadingShort!!.u
                    if (!cr.isNullOrEmpty()) {
                        todayDao.lhReadingShortInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhReadingShortUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhReadingShortDeleteAll(de)
                    }
                }
                if (crudLHResponsoryShort != null) {
                    val de = crudLHResponsoryShort!!.d
                    val cr = crudLHResponsoryShort!!.c
                    val up = crudLHResponsoryShort!!.u
                    if (!cr.isNullOrEmpty()) {
                        todayDao.lhResponsoryShortInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhResponsoryShortUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhResponsoryShortDeleteAll(de)
                    }
                }
                if (crudLHReadingShortJoin != null) {
                    val de = crudLHReadingShortJoin!!.d
                    val cr = crudLHReadingShortJoin!!.c
                    val up = crudLHReadingShortJoin!!.u
                    if (!cr.isNullOrEmpty()) {
                        todayDao.lhReadingShortJoinInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhReadingShortJoinUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhReadingShortJoinDeleteAll(de)
                    }
                }
                if (crudPrayer != null) {
                    val cr = crudPrayer!!.c
                    val up = crudPrayer!!.u
                    val de = crudPrayer!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.prayerInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.prayerUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.prayerDeleteAll(de)
                    }
                }
                if (crudLHPrayer != null) {
                    val cr = crudLHPrayer!!.c
                    val up = crudLHPrayer!!.u
                    val de = crudLHPrayer!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.lhPrayerInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhPrayerUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhPrayerDeleteAll(de)
                    }
                }

                if (crudLiturgyHomilyJoin != null) {
                    val cr = crudLiturgyHomilyJoin!!.c
                    val up = crudLiturgyHomilyJoin!!.u
                    val de = crudLiturgyHomilyJoin!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.liturgyHomilyJoinInsertAll(cr)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.liturgyHomilyJoinDeleteAll(de)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.liturgyHomilyJoinUpdateAll(up)
                    }
                }
                if (crudLiturgySaintJoin != null) {
                    val cr = crudLiturgySaintJoin!!.c
                    val up = crudLiturgySaintJoin!!.u
                    val de = crudLiturgySaintJoin!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.liturgySaintJoinInsertAll(cr)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.liturgySaintJoinDeleteAll(de)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.liturgySaintJoinUpdateAll(up)
                    }
                }

                if (crudBibleHomilyJoin != null) {
                    val cr = crudBibleHomilyJoin!!.c
                    val up = crudBibleHomilyJoin!!.u
                    val de = crudBibleHomilyJoin!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.bibleHomilyJoinInsertAll(cr)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.bibleHomilyJoinDeleteAll(de)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.bibleHomilyJoinUpdateAll(up)
                    }
                }
                if (crudLHGospelCanticle != null) {
                    val cr = crudLHGospelCanticle!!.c
                    val up = crudLHGospelCanticle!!.u
                    val de = crudLHGospelCanticle!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.gospelCanticleInsertAll(cr)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.gospelCanticleDeleteAll(de)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.gospelCanticleUpdateAll(up)
                    }
                }
                if (crudLHIntercessions != null) {
                    val cr = crudLHIntercessions!!.c
                    val up = crudLHIntercessions!!.u
                    val de = crudLHIntercessions!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.lhIntercessionsInsertAll(cr)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhIntercessionsDeleteAll(de)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhIntercessionsUpdateAll(up)
                    }
                }
                if (crudLHIntercessionsJoin != null) {
                    val cr = crudLHIntercessionsJoin!!.c
                    val up = crudLHIntercessionsJoin!!.u
                    val de = crudLHIntercessionsJoin!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.lhIntercessionsJoinInsertAll(cr)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhIntercessionsJoinDeleteAll(de)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhIntercessionsJoinUpdateAll(up)
                    }
                }


                if (crudBibleHomilyTheme != null) {
                    val cr = crudBibleHomilyTheme!!.c
                    val up = crudBibleHomilyTheme!!.u
                    val de = crudBibleHomilyTheme!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.bibleHomilyThemeInsertAll(cr)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.bibleHomilyThemeDeleteAll(de)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.bibleHomilyThemeUpdateAll(up)
                    }
                }
                if (crudBibleHomilyJoin != null) {
                    val cr = crudBibleHomilyJoin!!.c
                    val up = crudBibleHomilyJoin!!.u
                    val de = crudBibleHomilyJoin!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.bibleHomilyJoinInsertAll(cr)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.bibleHomilyJoinDeleteAll(de)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.bibleHomilyJoinUpdateAll(up)
                    }
                }


                /*
                            if (crudLHInvitatory != null) {
                                val cr = crudLHInvitatory!!.c
                                val up = crudLHInvitatory!!.u
                                val de = crudLHInvitatory!!.d
                                if (!cr.isNullOrEmpty()) {
                                    mTodayDao.lhInvitatoryInsertAll(cr)
                                }
                                if (!up.isNullOrEmpty()) {
                                    mTodayDao.lhInvitatoryUpdateAll(up)
                                }
                                if (!de.isNullOrEmpty()) {
                                    mTodayDao.lhInvitatoryDeleteAll(de)
                                }
                            }
                */
                if (crudLHKyrieJoin != null) {
                    val cr = crudLHKyrieJoin!!.c
                    val up = crudLHKyrieJoin!!.u
                    val de = crudLHKyrieJoin!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.lhKyrieJoinInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhKyrieJoinUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhKyrieJoinDeleteAll(de)
                    }
                }

                if (crudLHVirginAntiphonJoin != null) {
                    val cr = crudLHVirginAntiphonJoin!!.c
                    val up = crudLHVirginAntiphonJoin!!.u
                    val de = crudLHVirginAntiphonJoin!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.lhVirginAntiphonJoinInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhVirginAntiphonJoinUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhVirginAntiphonJoinDeleteAll(de)
                    }
                }

                if (crudLHNightPrayer != null) {
                    val cr = crudLHNightPrayer!!.c
                    val up = crudLHNightPrayer!!.u
                    val de = crudLHNightPrayer!!.d
                    if (!cr.isNullOrEmpty()) {
                        todayDao.lhNightPrayerInsertAll(cr)
                    }
                    if (!up.isNullOrEmpty()) {
                        todayDao.lhNightPrayerUpdateAll(up)
                    }
                    if (!de.isNullOrEmpty()) {
                        todayDao.lhNightPrayerDeleteAll(de)
                    }
                }

            if (crudUniversalis != null) {

                val cr = crudUniversalis!!.c
                val up = crudUniversalis!!.u
                val de = crudUniversalis!!.d
                if (!cr.isNullOrEmpty()) {
                    todayDao.universalisInsertAll(cr)
                }
                if (!up.isNullOrEmpty()) {
                    todayDao.universalisUpdateAll(up)
                }
                if (!de.isNullOrEmpty()) {
                    todayDao.universalisDeleteAll(de)
                }
            }


        } catch (e: Exception) {
            Log.d("CrudError", e.message!!)
        }
    }
}