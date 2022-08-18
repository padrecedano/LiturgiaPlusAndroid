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
    private Hoy today;
    public List<List<ComentarioBiblico>> allComentarios;
    private BiblicaMisa biblica;

    @PropertyName("comentarios")
    public List<ComentarioBiblico> comentarios;

    public Comentarios() {
    }

    public MetaLiturgia getMetaLiturgia() {
        return metaLiturgia;
    }

    public void setMetaLiturgia(MetaLiturgia metaLiturgia) {
        this.metaLiturgia = metaLiturgia;
    }

    @PropertyName("comentarios")
    public void setComentarios(List<ComentarioBiblico> comentarios) {
        this.comentarios = comentarios;
    }

    public void setAllComentarios(List<List<ComentarioBiblico>> comentarios) {
        this.allComentarios = comentarios;
    }

    @PropertyName("comentarios")
    public List<ComentarioBiblico> getComentarios() {
        return this.comentarios;
    }

    private SpannableStringBuilder getTitulo() {
        return Utils.toH3Red("COMENTARIOS EVANGELIO");
    }

    private String getTituloForRead() {
        return "Comentarios del Evangelio del día.";
    }

    public SpannableStringBuilder getAllForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder();

        //try {
        //sb.append(metaLiturgia.getAll());
        sb.append(LS2);
        sb.append(getTitulo());
        sb.append(LS2);
        //sb.append(Utils.formatSubTitle(getPericopa()));
        sb.append(LS2);
        for (ComentarioBiblico item : comentarios) {
            sb.append(item.getAllForView());
        }
        //} catch (Exception e) {
        //    sb.append(e.getMessage());
        //}
        return sb;
    }

    public SpannableStringBuilder getAllForViewNew() {
        SpannableStringBuilder sb = new SpannableStringBuilder();

        //try {
        //sb.append(metaLiturgia.getAll());
        sb.append(LS2);
        sb.append(getTitulo());
        sb.append(LS2);
        //sb.append(Utils.formatSubTitle(getPericopa()));
        sb.append(LS2);
        int i=0;
        for (List<ComentarioBiblico> subList : allComentarios) {

            if (subList.size() > 0) {

                for (ComentarioBiblico item : subList) {
                    sb.append(item.getAllForView());
                }
                //sb.append("COMENTARIOS.*");
                //sb.append(LS2);
            }
        }
        //} catch (Exception e) {
        //    sb.append(e.getMessage());
        //}
        return sb;
    }

    public StringBuilder getAllForRead() {
        StringBuilder sb = new StringBuilder();
        try {

            sb.append(metaLiturgia.getAllForRead());
            sb.append(getTituloForRead());
            sb.append(getPericopa());
            sb.append(".");
            for (ComentarioBiblico item : comentarios) {
                sb.append(item.getAllForRead());
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }
        return sb;
    }

    @SuppressWarnings("unused")
    public void setPericopa(String pericopa) {
        this.pericopa = pericopa;
    }

    public String getPericopa() {
        return this.pericopa;
    }

    public void setHoy(Hoy today) {
        this.today = today;
    }

    public BiblicaMisa getBiblica() {
        return biblica;
    }

    public void setBiblica(BiblicaMisa biblica) {
        this.biblica = biblica;
    }

}
