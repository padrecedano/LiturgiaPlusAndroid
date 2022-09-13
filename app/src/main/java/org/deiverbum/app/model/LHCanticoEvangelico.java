package org.deiverbum.app.model;

public class LHCanticoEvangelico {
    public Integer groupID=0;
    public Integer antiphonFK=0;

    @SuppressWarnings("unused")
    public LHCanticoEvangelico() {}

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer grupoId) {
        this.groupID = grupoId;
    }

    public Integer getAntiphonFK() {
        return antiphonFK;
    }

    public void setAntiphonFK(Integer antifonaFK) {
        this.antiphonFK = antifonaFK;
    }
}
