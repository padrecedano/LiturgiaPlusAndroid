package org.deiverbum.app.model;

public class LHCanticoEvangelico {
    public Integer groupID=0;
    public Integer antiphonFK=0;

    @SuppressWarnings("unused")
    public LHCanticoEvangelico() {}

    public Integer getGrupoId() {
        return groupID;
    }

    public void setGrupoId(Integer grupoId) {
        this.groupID = grupoId;
    }

    public Integer getAntifonaFK() {
        return antiphonFK;
    }

    public void setAntifonaFK(Integer antifonaFK) {
        this.antiphonFK = antifonaFK;
    }
}
