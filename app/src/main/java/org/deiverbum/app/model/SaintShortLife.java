package org.deiverbum.app.model;

public class SaintShortLife {
    public Integer saintFK=0;
    public String shortLife="";

    @SuppressWarnings("unused")
    public SaintShortLife() {}

    public Integer getSaintFK() {
        return saintFK;
    }

    public void setSaintFK(Integer saintFK) {
        this.saintFK = saintFK;
    }

    public String getShortLife() {
        return shortLife;
    }

    public void setShortLife(String shortLife) {
        this.shortLife = shortLife;
    }

}
