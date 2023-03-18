package org.deiverbum.app.model;

public class LiturgySaintJoin {
    public Integer liturgyFK;
    public Integer saintFK;

    @SuppressWarnings("unused")
    public LiturgySaintJoin() {}
    public Integer getLiturgyFK() {
        return liturgyFK;
    }

    public void setLiturgyFK(Integer liturgyFK) {
        this.liturgyFK = liturgyFK;
    }

    public Integer getSaintFK() {
        return saintFK;
    }

    public void setSaintFK(Integer saintFK) {
        this.saintFK = saintFK;
    }

}
