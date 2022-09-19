package org.deiverbum.app.data.wrappers;

import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.Homilia;
import org.deiverbum.app.model.LHAntifona;
import org.deiverbum.app.model.LHBiblicaBreveJoin;
import org.deiverbum.app.model.LHCanticoEvangelico;
import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.model.LiturgiaHomiliaJoin;
import org.deiverbum.app.model.MisaLectura;
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
    public List<Homilia> homilia;
    public List<SyncStatus> syncStatus;

    public Integer liturgia;
    public Integer massReading;

    public List<LiturgiaHomiliaJoin> liturgiaHomiliaJoin;
    public List<Liturgia> liturgiaa;
    public List<Today> today;
    public String lastUpdate;

    @SerializedName("mass_reading")
    public List<MisaLectura> misaLectura;

    @SerializedName("lhCanticoEvangelico")
    public List<LHCanticoEvangelico> ce;
    @SerializedName("lhBiblicaBreveJoin")
    public List<LHBiblicaBreveJoin> bvJoin;

    @SerializedName("lhAntifona")
    public List<LHAntifona> lhAntifona;

    @SerializedName("bibleReading")
    public List<Biblica> bibleReading;

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
