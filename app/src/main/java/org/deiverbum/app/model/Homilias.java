package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.List;

public class Homilias {
    public String padre;
    public String texto;
    public MetaLiturgia metaLiturgia;
    public List<Homilia> homilias;

    public Homilias(){}

    public MetaLiturgia getMetaLiturgia() {
        return metaLiturgia;
    }

    public void setMetaLiturgia(MetaLiturgia metaLiturgia) {
        this.metaLiturgia = metaLiturgia;
    }

    public void setHomilias(List<Homilia> homilias) {
        this.homilias=homilias;
    }

    public List<Homilia> getHomilias(){
        return this.homilias;
    }

    @SuppressWarnings("unused")
    private SpannableStringBuilder getTitulo() {
        return Utils.toH3Red("HOMILÍAS");
    }
    private String getTituloForRead() {
        return "Homilías.";
    }


    public SpannableStringBuilder getForView(){
        SpannableStringBuilder sb=new SpannableStringBuilder();

        try {

        sb.append(metaLiturgia.getAll());
        sb.append(LS2);
        for (Homilia h : homilias) {
            sb.append(h.getAllForView());
        }
        }catch (Exception e){
            sb.append(e.getMessage());

        }
        return sb;
    }

    public StringBuilder getAllForRead(){
        StringBuilder sb=new StringBuilder();
        sb.append(metaLiturgia.getAllForRead());
        sb.append(getTituloForRead());
        try {
        for (Homilia s : homilias) {
            sb.append(s.getAllForRead());
            /*sb.append(s.getPadre());
            sb.append(LS2);
            sb.append(s.getHomilia());
            sb.append(LS2);*/
        }
        }catch (Exception e){
            sb.append(e.getMessage());

        }
        return sb;
    }

}
