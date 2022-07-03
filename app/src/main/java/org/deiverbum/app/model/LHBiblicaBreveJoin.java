package org.deiverbum.app.model;

public class LHBiblicaBreveJoin {
    public Integer grupoId=0;
    public Integer lecturaFK=0;
    public Integer responsorioFK=0;

    @SuppressWarnings("unused")
    public LHBiblicaBreveJoin() {}

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }

    public Integer getLecturaFK() {
        return lecturaFK;
    }

    public void setLecturaFK(Integer lecturaFK) {
        this.lecturaFK = lecturaFK;
    }

    public Integer getResponsorioFK() {
        return responsorioFK;
    }

    public void setResponsorioFK(Integer responsorioFK) {
        this.responsorioFK = responsorioFK;
    }
}
