package org.deiverbum.app.data.wrappers;

import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.model.Biblical;
import org.deiverbum.app.model.HomilyList;
import org.deiverbum.app.model.LHGospelCanticle;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.SaintLife;
import org.deiverbum.app.model.SyncStatus;
import org.deiverbum.app.model.Today;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class SyncRequest {

    public List<HomilyList> homilia;
    public List<SyncStatus> syncStatus;

    public Integer liturgia;
    public Integer massReading;

    public List<Liturgy> liturgiaa;
    public List<Today> today;
    public String lastUpdate;


    @SerializedName("lhCanticoEvangelico")
    public List<LHGospelCanticle> ce;

    public List<Biblical> bibleReading;

    public List<SaintLife> saintLife;

    public SyncRequest() {
    }


}
