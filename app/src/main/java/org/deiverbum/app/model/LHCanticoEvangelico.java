package org.deiverbum.app.model;

public class LHCanticoEvangelico {
    public Integer grupoId=0;
    public Integer antifonaFK=0;

    @SuppressWarnings("unused")
    public LHCanticoEvangelico() {}

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }

    public Integer getAntifonaFK() {
        return antifonaFK;
    }

    public void setAntifonaFK(Integer antifonaFK) {
        this.antifonaFK = antifonaFK;
    }
}
