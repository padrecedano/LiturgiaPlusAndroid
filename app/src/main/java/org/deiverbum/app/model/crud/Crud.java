package org.deiverbum.app.model.crud;

import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.model.BibleHomilyJoin;
import org.deiverbum.app.model.BibleHomilyTheme;
import org.deiverbum.app.model.Biblical;
import org.deiverbum.app.model.Homily;
import org.deiverbum.app.model.HomilyList;
import org.deiverbum.app.model.LHAntiphon;
import org.deiverbum.app.model.LHEpigraph;
import org.deiverbum.app.model.LHGospelCanticleTable;
import org.deiverbum.app.model.LHHymn;
import org.deiverbum.app.model.LHHymnJoin;
import org.deiverbum.app.model.LHIntercession;
import org.deiverbum.app.model.LHIntercessionsJoin;
import org.deiverbum.app.model.LHInvitatoryJoin;
import org.deiverbum.app.model.LHOfficeBiblicalJoin;
import org.deiverbum.app.model.LHOfficeBiblicalTable;
import org.deiverbum.app.model.LHOfficePatristic;
import org.deiverbum.app.model.LHOfficePatristicJoin;
import org.deiverbum.app.model.LHOfficeVerse;
import org.deiverbum.app.model.LHOfficeVerseJoin;
import org.deiverbum.app.model.LHPrayer;
import org.deiverbum.app.model.LHPsalm;
import org.deiverbum.app.model.LHPsalmody;
import org.deiverbum.app.model.LHPsalmodyJoin;
import org.deiverbum.app.model.LHReadingShort;
import org.deiverbum.app.model.LHReadingShortJoin;
import org.deiverbum.app.model.LHResponsoryShort;
import org.deiverbum.app.model.LHResponsoryTable;
import org.deiverbum.app.model.LHTheme;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.LiturgyHomilyJoin;
import org.deiverbum.app.model.MassReading;
import org.deiverbum.app.model.MassReadingJoin;
import org.deiverbum.app.model.MassReadingTable;
import org.deiverbum.app.model.Pater;
import org.deiverbum.app.model.PaterOpus;
import org.deiverbum.app.model.Prayer;
import org.deiverbum.app.model.Saint;
import org.deiverbum.app.model.SaintLife;
import org.deiverbum.app.model.SyncStatus;
import org.deiverbum.app.model.Today;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class Crud  {

    public List<HomilyList> homily;

    public List<SyncStatus> syncStatus;

    public String lastUpdate;

    public boolean haveData;

    public List<Today> today;
    public CrudLiturgy crudLiturgy;

    public CrudToday crudToday;

    public CrudSaint crudSaint;

    public CrudLHInvitatoryJoin crudLHInvitatoryJoin;

    public CrudLHHymn crudLHHymn;

    public CrudLHHymnJoin crudLHHymnJoin;

    public CrudLHPsalmody crudLHPsalmody;
    public CrudLHPsalmodyJoin crudLHPsalmodyJoin;

    public CrudLHAntiphon crudLHAntiphon;
    public CrudLHTheme crudLHTheme;
    public CrudLHEpigraph crudLHEpigraph;
    public CrudLHPsalm crudLHPsalm;
    public CrudLHReadingShort crudLHReadingShort;
    public CrudLHResponsoryShort crudLHResponsoryShort;
    public CrudLHReadingShortJoin crudLHReadingShortJoin;

    public CrudLHOfficeVerse crudLHOfficeVerse;
    public CrudLHOfficeVerseJoin crudLHOfficeVerseJoin;
    public CrudLHOfficeBiblical crudLHOfficeBiblical;
    public CrudLHOfficeBiblicalJoin crudLHOfficeBiblicalJoin;
    public CrudLHResponsory crudLHResponsory;
    public CrudLHOfficePatristic crudLHOfficePatristic;
    public CrudLHOfficePatristicJoin crudLHOfficePatristicJoin;
    public CrudLHGospelCanticle crudLHGospelCanticle;
    public CrudLHIntercessions crudLHIntercessions;
    public CrudLHIntercessionsJoin crudLHIntercessionsJoin;

    public CrudPrayer crudPrayer;
    public CrudLHPrayer crudLHPrayer;

    public CrudBibleReading crudBibleReading;
    public CrudMassReading crudMassReading;
    public CrudMassReadingJoin crudMassReadingJoin;


    public CrudBibleHomilyJoin crudBibleHomilyJoin;
    public CrudBibleHomilyTheme crudBibleHomilyTheme;

    public CrudPater crudPater;
    public CrudHomily crudHomily;
    public CrudLiturgiaHomiliaJoin crudLiturgyHomilyJoin;


    public CrudPaterOpus crudPaterOpus;
    @SuppressWarnings("unused")
    public Crud() {
    }

    public void doCrud(TodayDao mTodayDao){

        if (crudLiturgy != null) {
            List<Liturgy> c = crudLiturgy.c;
            List<Liturgy> u = crudLiturgy.u;
            List<Liturgy> d = crudLiturgy.d;

            if (c != null && !c.isEmpty()) {
                mTodayDao.liturgyInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.liturgyUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.liturgyDeleteAll(d);
            }
        }

        if (crudSaint != null) {
            List<Saint> cs = crudSaint.cSaint;
            List<Saint> us = crudSaint.uSaint;
            List<Saint> ds = crudSaint.dSaint;
            List<SaintLife> cl = crudSaint.cLife;
            List<SaintLife> ul = crudSaint.uLife;
            List<SaintLife> dl = crudSaint.dLife;

            if (cs != null && !cs.isEmpty()) {
                mTodayDao.saintInsertAll(cs);
            }
            if (us != null && !us.isEmpty()) {
                mTodayDao.saintUpdateAll(us);
            }
            if (ds != null && !ds.isEmpty()) {
                mTodayDao.saintDeleteAll(ds);
            }

            if (cl != null && !cl.isEmpty()) {
                mTodayDao.saintLifeInsertAll(cl);
            }
            if (ul != null && !ul.isEmpty()) {
                mTodayDao.saintLifeUpdateAll(ul);
            }
            if (dl != null && !dl.isEmpty()) {
                mTodayDao.saintLifeDeleteAll(dl);
            }
        }

        if (crudLHInvitatoryJoin != null) {
            List<LHInvitatoryJoin> c = crudLHInvitatoryJoin.c;
            List<LHInvitatoryJoin> u = crudLHInvitatoryJoin.u;
            List<LHInvitatoryJoin> d = crudLHInvitatoryJoin.d;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhInvitatoryJoinInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhInvitatoryJoinUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.lhInvitatoryJoinDeleteAll(d);
            }
        }

        if (crudLHHymn != null) {
            List<LHHymn> c = crudLHHymn.c;
            List<LHHymn> u = crudLHHymn.u;
            List<LHHymn> d = crudLHHymn.d;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhHymnInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhHymnUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.lhHymnDeleteAll(d);
            }
        }

        if (crudLHHymnJoin != null) {
            List<LHHymnJoin> c = crudLHHymnJoin.c;
            List<LHHymnJoin> u = crudLHHymnJoin.u;
            List<LHHymnJoin> d = crudLHHymnJoin.d;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhHymnJoinInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhHymnJoinUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.lhHymnJoinDeleteAll(d);
            }
        }
        if (crudLHOfficeVerse != null) {
            List<LHOfficeVerse> c = crudLHOfficeVerse.c;
            List<LHOfficeVerse> u = crudLHOfficeVerse.u;
            List<LHOfficeVerse> d = crudLHOfficeVerse.d;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhOfficeVerseInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhOfficeVerseUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.lhOfficeVerseDeleteAll(d);
            }
        }

        if (crudLHOfficeVerseJoin != null) {
            List<LHOfficeVerseJoin> c = crudLHOfficeVerseJoin.c;
            List<LHOfficeVerseJoin> u = crudLHOfficeVerseJoin.u;
            List<LHOfficeVerseJoin> d = crudLHOfficeVerseJoin.d;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhOfficeVerseJoinInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhOfficeVerseJoinUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.lhOfficeVerseJoinDeleteAll(d);
            }
        }

        if (crudLHOfficeBiblical != null) {
            List<LHOfficeBiblicalTable> c = crudLHOfficeBiblical.c;
            List<LHOfficeBiblicalTable> u = crudLHOfficeBiblical.u;
            List<LHOfficeBiblicalTable> d = crudLHOfficeBiblical.d;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhOfficeBiblicalInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhOfficeBiblicalUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.lhOfficeBiblicalDeleteAll(d);
            }
        }

        if (crudLHOfficeBiblicalJoin != null) {
            List<LHOfficeBiblicalJoin> c = crudLHOfficeBiblicalJoin.c;
            List<LHOfficeBiblicalJoin> u = crudLHOfficeBiblicalJoin.u;
            List<LHOfficeBiblicalJoin> d = crudLHOfficeBiblicalJoin.d;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhOfficeBiblicalJoinInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhOfficeBiblicalJoinUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.lhOfficeBiblicalJoinDeleteAll(d);
            }
        }

        if (crudLHResponsory != null) {
            List<LHResponsoryTable> c = crudLHResponsory.c;
            List<LHResponsoryTable> u = crudLHResponsory.u;
            List<LHResponsoryTable> d = crudLHResponsory.d;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhResponsoryInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhResponsoryUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.lhResponsoryDeleteAll(d);
            }
        }

        if (crudLHOfficePatristic != null) {
            List<LHOfficePatristic> c = crudLHOfficePatristic.c;
            List<LHOfficePatristic> u = crudLHOfficePatristic.u;
            List<LHOfficePatristic> d = crudLHOfficePatristic.d;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhOfficePatristicInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhOfficePatristicUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.lhOfficePatristicDeleteAll(d);
            }
        }

        if (crudLHOfficePatristicJoin != null) {
            List<LHOfficePatristicJoin> c = crudLHOfficePatristicJoin.c;
            List<LHOfficePatristicJoin> u = crudLHOfficePatristicJoin.u;
            List<LHOfficePatristicJoin> d = crudLHOfficePatristicJoin.d;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhOfficePatristicJoinInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhOfficePatristicJoinUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.lhOfficePatristicJoinDeleteAll(d);
            }
        }

        if (crudLHPsalmody != null) {
            List<LHPsalmody> c = crudLHPsalmody.c;
            List<LHPsalmody> u = crudLHPsalmody.u;
            List<LHPsalmody> d = crudLHPsalmody.d;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhPsalmodyInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhPsalmodyUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.lhPsalmodyDeleteAll(d);
            }
        }

        if (crudLHPsalmodyJoin != null) {
            List<LHPsalmodyJoin> c = crudLHPsalmodyJoin.c;
            List<LHPsalmodyJoin> u = crudLHPsalmodyJoin.u;
            List<LHPsalmodyJoin> d = crudLHPsalmodyJoin.d;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhPsalmodyJoinInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhPsalmodyJoinUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.lhPsalmodyJoinDeleteAll(d);
            }
        }

        if (crudLHAntiphon != null) {
            List<LHAntiphon> d = crudLHAntiphon.d;
            List<LHAntiphon> c = crudLHAntiphon.c;
            List<LHAntiphon> u = crudLHAntiphon.u;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhAntiphonInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhAntiphonUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.lhAntiphonDeleteAll(d);
            }
        }

        if (crudLHTheme != null) {
            List<LHTheme> d = crudLHTheme.d;
            List<LHTheme> c = crudLHTheme.c;
            List<LHTheme> u = crudLHTheme.u;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhThemeInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhThemeUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.lhThemeDeleteAll(d);
            }
        }

        if (crudLHEpigraph != null) {
            List<LHEpigraph> d = crudLHEpigraph.d;
            List<LHEpigraph> c = crudLHEpigraph.c;
            List<LHEpigraph> u = crudLHEpigraph.u;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhEpigraphInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhEpigraphUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.lhEpigraphDeleteAll(d);
            }
        }

        if (crudLHPsalm != null) {
            List<LHPsalm> d = crudLHPsalm.d;
            List<LHPsalm> c = crudLHPsalm.c;
            List<LHPsalm> u = crudLHPsalm.u;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhPsalmInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhPsalmUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.lhPsalmDeleteAll(d);
            }
        }

        if (crudLHReadingShort != null) {
            List<LHReadingShort> d = crudLHReadingShort.d;
            List<LHReadingShort> c = crudLHReadingShort.c;
            List<LHReadingShort> u = crudLHReadingShort.u;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhReadingShortInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhReadingShortUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.lhReadingShortDeleteAll(d);
            }
        }

        if (crudLHResponsoryShort != null) {
            List<LHResponsoryShort> d = crudLHResponsoryShort.d;
            List<LHResponsoryShort> c = crudLHResponsoryShort.c;
            List<LHResponsoryShort> u = crudLHResponsoryShort.u;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhResponsoryShortInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhResponsoryShortUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.lhResponsoryShortDeleteAll(d);
            }
        }

        if (crudLHReadingShortJoin != null) {
            List<LHReadingShortJoin> d = crudLHReadingShortJoin.d;
            List<LHReadingShortJoin> c = crudLHReadingShortJoin.c;
            List<LHReadingShortJoin> u = crudLHReadingShortJoin.u;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhReadingShortJoinInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhReadingShortJoinUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.lhReadingShortJoinDeleteAll(d);
            }
        }

        if (crudPrayer != null) {
            List<Prayer> c = crudPrayer.c;
            List<Prayer> u = crudPrayer.u;
            List<Prayer> d = crudPrayer.d;

            if (c != null && !c.isEmpty()) {
                mTodayDao.prayerInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.prayerUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.prayerDeleteAll(d);
            }
        }

        if (crudLHPrayer != null) {
            List<LHPrayer> c = crudLHPrayer.c;
            List<LHPrayer> u = crudLHPrayer.u;
            List<LHPrayer> d = crudLHPrayer.d;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhPrayerInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhPrayerUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.lhPrayerDeleteAll(d);
            }
        }

        if (crudPater != null) {
            List<Pater> c = crudPater.c;
            List<Pater> u = crudPater.u;
            List<Pater> d = crudPater.d;
            if (c != null && !c.isEmpty()) {
                mTodayDao.paterInsertAll(c);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.paterDeleteAll(d);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.paterUpdateAll(u);
            }
        }

        if (crudHomily != null) {
            List<Homily> c = crudHomily.c;
            List<Homily> u = crudHomily.u;
            List<Homily> d = crudHomily.d;
            if (c != null && !c.isEmpty()) {
                mTodayDao.homilyInsertAll(c);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.homilyDeleteAll(d);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.homilyUpdateAll(u);
            }
        }

        if (crudLiturgyHomilyJoin != null) {
            List<LiturgyHomilyJoin> c = crudLiturgyHomilyJoin.c;
            List<LiturgyHomilyJoin> u = crudLiturgyHomilyJoin.u;
            List<LiturgyHomilyJoin> d = crudLiturgyHomilyJoin.d;
            if (c != null && !c.isEmpty()) {
                mTodayDao.liturgyHomilyJoinInsertAll(c);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.liturgyHomilyJoinDeleteAll(d);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.liturgyHomilyJoinUpdateAll(u);
            }
        }

        if (crudBibleReading != null) {
            List<Biblical> c = crudBibleReading.c;
            List<Biblical> u = crudBibleReading.u;
            List<Biblical> d = crudBibleReading.d;

            if (c != null && !c.isEmpty()) {
                mTodayDao.bibleReadingInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.bibleReadingUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.bibleReadingDeleteAll(d);
            }
        }

        if (crudMassReading != null) {
            List<MassReadingTable> c = crudMassReading.c;
            List<MassReadingTable> u = crudMassReading.u;
            List<MassReadingTable> d = crudMassReading.d;
            if (c != null && !c.isEmpty()) {
                mTodayDao.massReadingInsertAll(c);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.massReadingDeleteAll(d);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.massReadingUpdateAll(u);
            }
        }


        if (crudBibleHomilyJoin != null) {
            List<BibleHomilyJoin> c = crudBibleHomilyJoin.c;
            List<BibleHomilyJoin> u = crudBibleHomilyJoin.u;
            List<BibleHomilyJoin> d = crudBibleHomilyJoin.d;
            if (c != null && !c.isEmpty()) {
                mTodayDao.bibleHomilyJoinInsertAll(c);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.bibleHomilyJoinDeleteAll(d);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.bibleHomilyJoinUpdateAll(u);
            }
        }
        if (crudLHGospelCanticle != null) {
            List<LHGospelCanticleTable> c = crudLHGospelCanticle.c;
            List<LHGospelCanticleTable> u = crudLHGospelCanticle.u;
            List<LHGospelCanticleTable> d = crudLHGospelCanticle.d;

            if (c != null && !c.isEmpty()) {
                mTodayDao.gospelCanticleInsertAll(c);
            }

            if (d != null && !d.isEmpty()) {
                mTodayDao.gospelCanticleDeleteAll(d);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.gospelCanticleUpdateAll(u);
            }
        }

        if (crudLHIntercessions != null) {
            List<LHIntercession> c = crudLHIntercessions.c;
            List<LHIntercession> u = crudLHIntercessions.u;
            List<LHIntercession> d = crudLHIntercessions.d;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhIntercessionsInsertAll(c);
            }

            if (d != null && !d.isEmpty()) {
                mTodayDao.lhIntercessionsDeleteAll(d);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhIntercessionsUpdateAll(u);
            }
        }

        if (crudLHIntercessionsJoin != null) {
            List<LHIntercessionsJoin> c = crudLHIntercessionsJoin.c;
            List<LHIntercessionsJoin> u = crudLHIntercessionsJoin.u;
            List<LHIntercessionsJoin> d = crudLHIntercessionsJoin.d;

            if (c != null && !c.isEmpty()) {
                mTodayDao.lhIntercessionsJoinInsertAll(c);
            }

            if (d != null && !d.isEmpty()) {
                mTodayDao.lhIntercessionsJoinDeleteAll(d);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.lhIntercessionsJoinUpdateAll(u);
            }
        }

        if (crudMassReadingJoin != null) {
            List<MassReadingJoin> c = crudMassReadingJoin.c;
            List<MassReadingJoin> u = crudMassReadingJoin.u;
            List<MassReadingJoin> d = crudMassReadingJoin.d;
            if (c != null && !c.isEmpty()) {
                mTodayDao.massReadingJoinInsertAll(c);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.massReadingJoinDeleteAll(d);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.massReadingJoinUpdateAll(u);
            }
        }
        if (crudBibleHomilyTheme != null) {
            List<BibleHomilyTheme> c = crudBibleHomilyTheme.c;
            List<BibleHomilyTheme> u = crudBibleHomilyTheme.u;
            List<BibleHomilyTheme> d = crudBibleHomilyTheme.d;
            if (c != null && !c.isEmpty()) {
                mTodayDao.bibleHomilyThemeInsertAll(c);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.bibleHomilyThemeDeleteAll(d);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.bibleHomilyThemeUpdateAll(u);
            }
        }

        if (crudBibleHomilyJoin != null) {
            List<BibleHomilyJoin> c = crudBibleHomilyJoin.c;
            List<BibleHomilyJoin> u = crudBibleHomilyJoin.u;
            List<BibleHomilyJoin> d = crudBibleHomilyJoin.d;
            if (c != null && !c.isEmpty()) {
                mTodayDao.bibleHomilyJoinInsertAll(c);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.bibleHomilyJoinDeleteAll(d);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.bibleHomilyJoinUpdateAll(u);
            }
        }



        if (crudPaterOpus != null) {
            List<PaterOpus> c = crudPaterOpus.c;
            List<PaterOpus> u = crudPaterOpus.u;
            List<PaterOpus> d = crudPaterOpus.d;
            if (c != null && !c.isEmpty()) {
                mTodayDao.paterOpusInsertAll(c);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.paterOpusDeleteAll(d);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.paterOpusUpdateAll(u);
            }
        }

        if (crudToday != null) {
            List<Today> c = crudToday.c;
            List<Today> u = crudToday.u;
            List<Today> d = crudToday.d;

            if (c != null && !c.isEmpty()) {
                mTodayDao.todayInsertAll(c);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.todayUpdateAll(u);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.todayDeleteAll(d);
            }
        }
    }        
        


}
