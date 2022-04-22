package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

/**
 * <p>Nueva clase para manejar libros de la Biblia en el contexto litúrgico.</p>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class BibliaLibro {
    private Integer id;
    private String name;
    private String liturgyName;
    private String shortName;

    public BibliaLibro() {
    }

    public BibliaLibro(Integer id, String name) {
        this.name = name;
        this.id = id;

    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLiturgyName() {
        return liturgyName;
    }

    public void setLiturgyName(String liturgyName) {
        this.liturgyName = liturgyName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




    public SpannableStringBuilder getForView() {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        //sb.append(Utils.fromHtml(getIntro()));
        return sb;
    }

    public StringBuilder getForRead() {
        StringBuilder sb=new StringBuilder();
        //sb.append(Utils.fromHtml(Utils.stripQuotation(getIntro())));
        return sb;
    }
}