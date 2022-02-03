package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.utils.Utils;

import java.util.List;
import java.util.Random;

public class Conclusion {

    @SerializedName("bendicion")
    @Expose
    private String bendicion;

    List<String> antVirgen;


    public Conclusion() {
    }

    public SpannableStringBuilder getHeader() {

        return Utils.formatTitle("CONCLUSIÓN");
    }

    public String getHeaderForRead() {
        return "CONCLUSIÓN.";
    }


    public String getBendicion() {
        return bendicion;
    }


    public String getBendicionForRead() {
        return "El Señor todopoderoso nos conceda una noche tranquila y una santa muerte. Amén.";
    }

    @SuppressWarnings("unused")
    public void setBendicion(String bendicion) {
        this.bendicion = bendicion;
    }


    public String getAntifonaVirgen(int timeID) {
        int mIndex = (timeID != 6) ? new Random().nextInt(3) : 4;

        return antVirgen.get(mIndex);

    }

    public SpannableStringBuilder getAll(int idTiempo) {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getHeader());
        sb.append(Utils.LS2);
        sb.append(getBendicion());
        sb.append(Utils.LS2);
        sb.append(Utils.formatTitle("ANTÍFONA FINAL DE LA SANTÍSIMA VIRGEN"));
        sb.append(Utils.LS2);
        sb.append(Utils.fromHtml(getAntifonaVirgen(idTiempo)));
        return sb;
    }

    public SpannableStringBuilder getAllForRead(int idTiempo) {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getHeaderForRead());
        sb.append(getBendicionForRead());
        sb.append("ANTÍFONA FINAL DE LA SANTÍSIMA VIRGEN.");
        sb.append(Utils.fromHtml(getAntifonaVirgen(idTiempo)));
        return sb;
    }
}

