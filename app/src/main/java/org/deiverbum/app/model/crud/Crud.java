package org.deiverbum.app.model.crud;

import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.model.BibleHomilyJoin;
import org.deiverbum.app.model.Biblical;
import org.deiverbum.app.model.Homily;
import org.deiverbum.app.model.HomilyList;
import org.deiverbum.app.model.LHAntiphon;
import org.deiverbum.app.model.LHEpigraph;
import org.deiverbum.app.model.LHGospelCanticleJoin;
import org.deiverbum.app.model.LHHymn;
import org.deiverbum.app.model.LHHymnJoin;
import org.deiverbum.app.model.LHInvitatoryJoin;
import org.deiverbum.app.model.LHOfficeVerse;
import org.deiverbum.app.model.LHOfficeVerseJoin;
import org.deiverbum.app.model.LHPsalm;
import org.deiverbum.app.model.LHPsalmody;
import org.deiverbum.app.model.LHTheme;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.LiturgyHomilyJoin;
import org.deiverbum.app.model.MassReading;
import org.deiverbum.app.model.MassReadingOLD;
import org.deiverbum.app.model.PaterOpus;
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

    @SerializedName("sync_status")
    public List<SyncStatus> syncStatus;

    @SerializedName("lastUpdate")
    public String lastUpdate;

    public boolean haveData;

    public List<Liturgy> liturgia;
    public List<Today> today;

    //@SerializedName("mass_reading")
    public List<MassReading> mass_reading;

    @SerializedName("crudToday")
    public CrudToday crudToday;

    @SerializedName("crudSaint")
    public CrudSaint crudSaint;

    @SerializedName("crudLHInvitatoryJoin")
    public CrudLHInvitatoryJoin crudLHInvitatoryJoin;

    @SerializedName("crudLHHymn")
    public CrudLHHymn crudLHHymn;

    @SerializedName("crudLHHymnJoin")
    public CrudLHHymnJoin crudLHHymnJoin;

    public CrudLHPsalmody crudLHPsalmody;
    public CrudLHAntiphon crudLHAntiphon;
    public CrudLHTheme crudLHTheme;
    public CrudLHEpigraph crudLHEpigraph;
    public CrudLHPsalm crudLHPsalm;

    @SerializedName("crudLHOfficeVerse")
    public CrudLHOfficeVerse crudLHOfficeVerse;

    @SerializedName("crudLHOfficeVerseJoin")
    public CrudLHOfficeVerseJoin crudLHOfficeVerseJoin;

    @SerializedName("crudBibleHomilyJoin")
    public CrudBibleHomilyJoin crudBibleHomilyJoin;

    @SerializedName("crudHomily")
    public CrudHomily crudHomily;

    @SerializedName("crudLHGospelCanticle")
    public CrudLHGospelCanticle ce;

    @SerializedName("bibleReading")
    public List<Biblical> bibleReading;

    @SerializedName("saintLife")
    public List<SaintLife> saintLife;

    @SerializedName("sync_liturgy_homily_join")
    public CrudLiturgiaHomiliaJoin homilyJoin;

    @SerializedName("sync_mass_reading")
    public CrudMassReading crudMassReading;

    @SerializedName("crudBibleReading")
    public CrudBibleReading crudBibleReading;

    public CrudPaterOpus crudPaterOpus;
    @SuppressWarnings("unused")
    public Crud() {
    }

    public void doCrud(TodayDao mTodayDao){

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


        if (homilyJoin != null) {
            List<LiturgyHomilyJoin> c = homilyJoin.c;
            List<LiturgyHomilyJoin> u = homilyJoin.u;
            List<LiturgyHomilyJoin> d = homilyJoin.d;
            if (c != null && !c.isEmpty()) {
                mTodayDao.homilyJoinInsertAll(c);
            }
            if (d != null && !d.isEmpty()) {
                mTodayDao.homilyJoinDeleteAll(d);
            }
            if (u != null && !u.isEmpty()) {
                mTodayDao.homilyJoinUpdateAll(u);
            }
        }

        if (crudMassReading != null) {
            List<MassReadingOLD> c = crudMassReading.c;
            List<MassReadingOLD> u = crudMassReading.u;
            List<MassReadingOLD> d = crudMassReading.d;
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
        if (ce != null) {
            List<LHGospelCanticleJoin> c = ce.c;
            List<LHGospelCanticleJoin> u = ce.u;
            List<LHGospelCanticleJoin> d = ce.d;

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
