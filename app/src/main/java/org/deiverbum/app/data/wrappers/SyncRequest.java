package org.deiverbum.app.data.wrappers;

import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.model.Biblical;
import org.deiverbum.app.model.HomilyList;
import org.deiverbum.app.model.LHAntiphon;
import org.deiverbum.app.model.LHReadingShortJoin;
import org.deiverbum.app.model.LHGospelCanticle;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.LiturgyHomilyJoin;
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
public class SyncRequest {

    private String table;
    private String action;
    public List<HomilyList> homilia;
    public List<SyncStatus> syncStatus;

    public Integer liturgia;
    public Integer massReading;

    public List<LiturgyHomilyJoin> liturgiaHomiliaJoin;
    public List<Liturgy> liturgiaa;
    public List<Today> today;
    public String lastUpdate;

    @SerializedName("mass_reading")
    public List<MassReadingOLD> misaLectura;

    @SerializedName("lhCanticoEvangelico")
    public List<LHGospelCanticle> ce;
    @SerializedName("lhBiblicaBreveJoin")
    public List<LHReadingShortJoin> bvJoin;

    @SerializedName("lhAntifona")
    public List<LHAntiphon> lhAntifona;

    @SerializedName("bibleReading")
    public List<Biblical> bibleReading;

    @SerializedName("saintLife")
    public List<SaintLife> saintLife;

    public SyncRequest() {
    }

    public SyncRequest(String table, String action) {
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
