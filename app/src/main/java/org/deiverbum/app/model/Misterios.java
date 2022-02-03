package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.List;

public class Misterios {
    public Gloriosos gloriosos;
    public Gozosos gozosos;
    public Luminosos luminosos;
    public Dolorosos dolorosos;
    public String titulo;

    public Misterios(){
    }
    public Gloriosos getGloriosos() {
        return gloriosos;
    }

    public void setGloriosos(Gloriosos gloriosos) {
        this.gloriosos = gloriosos;
    }

    public Gozosos getGozosos() {
        return gozosos;
    }

    public void setGozosos(Gozosos gozosos) {
        this.gozosos = gozosos;
    }

    public Luminosos getLuminosos() {
        return luminosos;
    }

    public void setLuminosos(Luminosos luminosos) {
        this.luminosos = luminosos;
    }

    public Dolorosos getDolorosos() {
        return dolorosos;
    }

    public void setDolorosos(Dolorosos dolorosos) {
        this.dolorosos = dolorosos;
    }


    public SpannableStringBuilder getText(List<String> list) {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        for (String s : list) {
            ssb.append(s);
            ssb.append(Utils.LS2);
        }
        return ssb;
    }


}
