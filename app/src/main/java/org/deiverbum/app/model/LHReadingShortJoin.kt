package org.deiverbum.app.model;

public class LHReadingShortJoin {
    public Integer groupID = 0;
    public Integer readingFK = 0;
    public Integer responsoryFK = 0;

    @SuppressWarnings("unused")
    public LHReadingShortJoin() {
    }

    public Integer getGrupoId() {
        return groupID;
    }

    public void setGrupoId(Integer grupoId) {
        this.groupID = grupoId;
    }

    public Integer getLecturaFK() {
        return readingFK;
    }

    public void setLecturaFK(Integer lecturaFK) {
        this.readingFK = lecturaFK;
    }

    public Integer getResponsorioFK() {
        return responsoryFK;
    }

    public void setResponsorioFK(Integer responsorioFK) {
        this.responsoryFK = responsorioFK;
    }
}