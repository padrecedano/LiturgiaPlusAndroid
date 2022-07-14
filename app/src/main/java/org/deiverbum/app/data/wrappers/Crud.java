package org.deiverbum.app.data.wrappers;

import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.BiblicaMisa;
import org.deiverbum.app.model.Homilia;
import org.deiverbum.app.model.LHAntifona;
import org.deiverbum.app.model.LHBiblicaBreveJoin;
import org.deiverbum.app.model.LHCanticoEvangelico;
import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.model.LiturgiaHomiliaJoin;
import org.deiverbum.app.model.MisaLectura;
import org.deiverbum.app.model.MisaLecturas;
import org.deiverbum.app.model.Today;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
public class Crud  {

    private String table;
    private String action;
    public List<Homilia> homilia;
    public List<LiturgiaHomiliaJoin> liturgiaHomiliaJoin;
    public List<Liturgia> liturgia;
    public List<Today> today;

    @SerializedName("misaLectura")
    public List<MisaLectura> misaLectura;

    @SerializedName("lhCanticoEvangelico")

    public List<LHCanticoEvangelico> ce;
    @SerializedName("lhBiblicaBreveJoin")
    public List<LHBiblicaBreveJoin> bvJoin;

    @SerializedName("lhAntifona")
    public List<LHAntifona> lhAntifona;

    @SerializedName("bibleReading")
    public List<Biblica> bibleReading;

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
