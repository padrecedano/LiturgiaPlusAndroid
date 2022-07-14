package org.deiverbum.app.model;

public class LHAntifona {
    public Integer antifonaId=0;
    public String antifona="";

    @SuppressWarnings("unused")
    public LHAntifona() {}

    public Integer getAntifonaId() {
        return antifonaId;
    }

    public void setAntifonaId(Integer antifonaId) {
        this.antifonaId = antifonaId;
    }

    public String getAntifona() {
        return antifona;
    }

    public void setAntifona(String antifona) {
        this.antifona = antifona;
    }

}
