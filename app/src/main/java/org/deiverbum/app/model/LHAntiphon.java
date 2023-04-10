package org.deiverbum.app.model;

public class LHAntiphon {
    public Integer antiphonID = 0;
    public String antiphon = "";

    @SuppressWarnings("unused")
    public LHAntiphon() {
    }

    public Integer getAntifonaId() {
        return antiphonID;
    }

    public void setAntifonaId(Integer antifonaId) {
        this.antiphonID = antifonaId;
    }

    public String getAntifona() {
        return antiphon;
    }

    public void setAntifona(String antifona) {
        this.antiphon = antifona;
    }
}