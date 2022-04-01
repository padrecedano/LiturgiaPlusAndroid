package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.PropertyName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;


public class Liturgia {
    private MetaLiturgia meta;
    private MetaLiturgia metaLiturgia;

    private Santo santo;
    private Misa misa;

    @SerializedName("lh")
    @Expose
    private Breviario breviario;
    private HashMap<String, Object> lhFirebase;

    private List<HomiliaCompleta> homiliaCompleta;
    @SuppressWarnings("unused")
    private List<ComentarioBiblico> comentarioCompleto;
    private Comentario comentario;

    public boolean hasSaint = false;


    public Liturgia() {
    }

    @SuppressWarnings("unused")
    public List<HomiliaCompleta> getHomiliaCompleta() {
        return homiliaCompleta;
    }

    @SuppressWarnings("unused")
    public List<ComentarioBiblico> getComentarioCompleta() {
        return comentarioCompleto;
    }

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }
    @SuppressWarnings("unused")
    public void setHomiliaCompleta(List<HomiliaCompleta> homiliaCompleta) {
        this.homiliaCompleta = homiliaCompleta;
    }



    public SpannableStringBuilder getVida() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        if (meta.getHasSaint()) {
            //ssb.append(santo.getVidaSmall());
            ssb.append("Método en <code>Liturgia</code> que será eleminado");
        }
        return ssb;
    }



    public Santo getSanto() {
        return santo;
    }

    public void setSanto(Santo santo) {
        this.santo = santo;
    }


    public Breviario getBreviario() {
        return this.breviario;
    }

    public void setBreviario(Breviario breviario) {
        //Log.d("-aaa",meta.getFecha());

        this.breviario = breviario;

        this.breviario.setMetaLiturgia(metaLiturgia);


    }

    @SuppressWarnings("unused")
    public HashMap<String, Object> getLh() {
        return lhFirebase;
    }

    @PropertyName("meta")
    public void setMetaLiturgia(MetaLiturgia meta) {
        this.metaLiturgia = meta;
        }

    @SuppressWarnings("unused")
    public void setLh(HashMap<String, Object> lh) {
        this.lhFirebase = lh;
    }

    @NonNull
    public String toString() {
        return "This is the data*: " + getMetaLiturgia().getFecha();
    }

    public MetaLiturgia getMetaLiturgia() {
        return this.metaLiturgia;
    }

    public MetaLiturgia getMeta() {
        return meta;
    }

    @SuppressWarnings("unused")
    public void setMetaLiturgias(MetaLiturgia meta) {
        this.meta = meta;
    }

    public Misa getMisa() {
        return misa;
    }
    public void setMisa(Misa misa) {
        this.misa = misa;
    }



}

