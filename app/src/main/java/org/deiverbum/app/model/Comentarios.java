package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import com.google.firebase.firestore.PropertyName;

import org.deiverbum.app.utils.Utils;

import java.util.List;

public class Comentarios {
    public String padre;
    public int id_homilia;
    public String pericopa;
    public MetaLiturgia metaLiturgia;

    @PropertyName("comentarios")
    public List<ComentarioBiblico> comentarios;

    public Comentarios(){}

    public MetaLiturgia getMetaLiturgia() {
        return metaLiturgia;
    }

    public void setMetaLiturgia(MetaLiturgia metaLiturgia) {
        this.metaLiturgia = metaLiturgia;
    }

    @PropertyName("comentarios")
    public void setComentarios(List<ComentarioBiblico> comentarios) {
        this.comentarios=comentarios;
    }

    @PropertyName("comentarios")
    public List<ComentarioBiblico> getComentarios(){
        return this.comentarios;
    }

    private SpannableStringBuilder getTitulo() {
        return Utils.toH3Red("COMENTARIOS EVANGELIO");
    }

    private String getTituloForRead() {
        return "Comentarios del Evangelio del día.";
    }

    public SpannableStringBuilder getAllForView(){
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(metaLiturgia.getAll());
        sb.append(LS2);
        sb.append(getTitulo());
        sb.append(LS2);
        sb.append(Utils.formatSubTitle(getPericopa()));
        sb.append(LS2);
        for (ComentarioBiblico item : comentarios) {
            sb.append(item.getAllForView());
        }
        return sb;
    }

    public StringBuilder getAllForRead(){
        StringBuilder sb=new StringBuilder();
        sb.append(metaLiturgia.getAllForRead());
        sb.append(getTituloForRead());
        sb.append(getPericopa());
        sb.append(".");
        for (ComentarioBiblico item : comentarios) {
            sb.append(item.getAllForRead());
        }
        return sb;
    }

    @SuppressWarnings("unused")
    public void setPericopa(String pericopa) {
         this.pericopa=pericopa;
    }
    public String getPericopa() {
        return this.pericopa;
    }





}
