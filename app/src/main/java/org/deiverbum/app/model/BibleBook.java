package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.normalizeEnd;

/**
 * <p>Nueva clase para manejar libros de la Biblia en el contexto litúrgico.</p>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class BibleBook {
    private Integer id;
    private String name;
    private String liturgyName;
    private String shortName;

    public BibleBook() {
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


    public String getForRead() {
        return normalizeEnd(getLiturgyName());
    }
}