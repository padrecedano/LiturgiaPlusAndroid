package org.deiverbum.app.data.wrappers;

import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.model.Homilia;
import org.deiverbum.app.model.LHBiblicaBreveJoin;
import org.deiverbum.app.model.LHCanticoEvangelico;
import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.model.LiturgiaHomiliaJoin;

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
    @SerializedName("lhCanticoEvangelico")

    public List<LHCanticoEvangelico> ce;
    @SerializedName("lhBiblicaBreveJoin")
    public List<LHBiblicaBreveJoin> bvJoin;

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
