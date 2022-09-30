package org.deiverbum.app.data.wrappers;

import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.model.Biblical;
import org.deiverbum.app.model.HomilyList;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.MassReading;
import org.deiverbum.app.model.SaintLife;
import org.deiverbum.app.model.SyncStatus;
import org.deiverbum.app.model.Today;
import org.deiverbum.app.model.crud.CrudBibleHomilyJoin;
import org.deiverbum.app.model.crud.CrudBibleReading;
import org.deiverbum.app.model.crud.CrudHomily;
import org.deiverbum.app.model.crud.CrudLHGospelCanticle;
import org.deiverbum.app.model.crud.CrudLHHymn;
import org.deiverbum.app.model.crud.CrudLHHymnJoin;
import org.deiverbum.app.model.crud.CrudLHInvitatoryJoin;
import org.deiverbum.app.model.crud.CrudLHOfficeVerseJoin;
import org.deiverbum.app.model.crud.CrudLiturgiaHomiliaJoin;
import org.deiverbum.app.model.crud.CrudMassReading;
import org.deiverbum.app.model.crud.CrudSaint;
import org.deiverbum.app.model.crud.CrudToday;

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

    @SuppressWarnings("unused")
    public Crud() {
    }

}
