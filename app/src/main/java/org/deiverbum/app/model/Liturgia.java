package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.google.firebase.firestore.PropertyName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
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
    private List<ComentarioBiblico> comentarioCompleto;
    private Comentario comentario;

    public boolean hasSaint = false;

    private ArrayList<String> times = new ArrayList<>(
            Arrays.asList("Adviento","Navidad"));

    public Liturgia() {
    }


    public List<HomiliaCompleta> getHomiliaCompleta() {
        return homiliaCompleta;
    }

    public List<ComentarioBiblico> getComentarioCompleta() {
        return comentarioCompleto;
    }

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }
    public void setHomiliaCompleta(List<HomiliaCompleta> homiliaCompleta) {
        this.homiliaCompleta = homiliaCompleta;
    }

    public String getTitulo() {

        if (hasSaint) {
            //return santo.getNombre() + "\n\n";
        } else {
            //return meta.getTitulo() + "\n\n";
        }
        return "Este método de <code>Liturgia</code> sera sacado.";
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
        //Log.d("-aaa",this.breviario.getMetaLiturgia().getFecha());

        this.breviario.setMetaLiturgia(metaLiturgia);
//        Log.d("-aaa",this.breviario.getMetaLiturgia().getFecha());

//        Log.d("-aaa",this.breviario.getMetaLiturgia().getFecha());

    }

    public HashMap<String, Object> getLh() {
        return lhFirebase;
    }

    /*
        @PropertyName("lh")ListList
        public void setLh(List<String> lh) {
            this.lh = lh;public void setLh(HashMap<String,Object> lh) {
        }*/

    @PropertyName("meta")
    public void setMetaLiturgia(MetaLiturgia meta) {
        this.metaLiturgia = meta;
        }

    public void setLh(HashMap<String, Object> lh) {
        this.lhFirebase = lh;
    }
    public String toString() {
        return "This is the data*: " + getMetaLiturgia().getFecha();
    }

    public MetaLiturgia getMetaLiturgia() {
        Log.d("mtl:Liturgia",getClass().getCanonicalName());
        //Log.d("mtl:Liturgia",this.meta.getFecha());
        return this.metaLiturgia;
    }

    public MetaLiturgia getMeta() {
        return meta;
    }
    public void setMetaLiturgias(MetaLiturgia meta) {
        this.meta = meta;
    }

    public String toStringg(){
        return "Test";
    }





    public Misa getMisa() {
        return misa;
    }
    public void setMisa(Misa misa) {
        this.misa = misa;
    }

public String getTimeById(int timeId){
        return "a";
}


}

