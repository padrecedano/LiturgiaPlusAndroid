package org.deiverbum.app.data.wrappers;

import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.model.Biblical;
import org.deiverbum.app.model.CrudBibleHomilyJoin;
import org.deiverbum.app.model.CrudBibleReading;
import org.deiverbum.app.model.CrudHomily;
import org.deiverbum.app.model.CrudMassReading;
import org.deiverbum.app.model.CrudToday;
import org.deiverbum.app.model.HomilyList;
import org.deiverbum.app.model.LHAntiphon;
import org.deiverbum.app.model.LHReadingShortJoin;
import org.deiverbum.app.model.LHGospelCanticleCrud;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.LiturgyHomilyJoin;
import org.deiverbum.app.model.LiturgiaHomiliaJoinCrud;
import org.deiverbum.app.model.MassReadingOLD;
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

    private String table;
    private String action;
    public List<HomilyList> homily;

    @SerializedName("sync_status")
    public List<SyncStatus> syncStatus;

    @SerializedName("toUpdate")
    public List<SyncStatus> updateList;

    @SerializedName("lastUpdate")
    public String lastUpdate;

    public String lastVersionLHJ;
    public boolean haveData;

    public List<LiturgyHomilyJoin> liturgiaHomiliaJoin;
    public List<Liturgy> liturgia;
    public List<Today> today;

    //@SerializedName("mass_reading")
    public List<MassReadingOLD> mass_reading;

    @SerializedName("crudToday")
    public CrudToday crudToday;

    @SerializedName("crudBibleHomilyJoin")
    public CrudBibleHomilyJoin crudBibleHomilyJoin;

    @SerializedName("crudHomily")
    public CrudHomily crudHomily;

    @SerializedName("crudLHGospelCanticle")
    public LHGospelCanticleCrud ce;

    @SerializedName("crud")
    public CrudWrapper crudWrapper;

    @SerializedName("ceWrapper")
    public CrudWrapper ceWrapper;

    @SerializedName("lhBiblicaBreveJoin")
    public List<LHReadingShortJoin> bvJoin;

    @SerializedName("lhAntifona")
    public List<LHAntiphon> lhAntifona;

    @SerializedName("bibleReading")
    public List<Biblical> bibleReading;

    @SerializedName("saintLife")
    public List<SaintLife> saintLife;

    @SerializedName("sync_liturgy_homily_join")
    public LiturgiaHomiliaJoinCrud homilyJoin;

    @SerializedName("sync_mass_reading")
    public CrudMassReading crudMassReading;

    @SerializedName("crudBibleReading")
    public CrudBibleReading crudBibleReading;
    //@SerializedName("c")
    //public List<LiturgyHomilyJoin> c;

    public Crud() {
    }

    public Crud(String table, String action) {
        this.table=table;
        this.action=action;
    }



    public String getTable() {
        return this.table;
    }
    public String getAction() {
        return this.action;
    }

}
