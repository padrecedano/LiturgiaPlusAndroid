package org.deiverbum.app.model;

public class Pater {
    public String pater;
    public Integer paterID;
    public String liturgyName;
    public Integer placeFK;
    public Integer typeFK;
    public Integer titleFK;
    public Integer missionFK;
    public Integer sexFK;
    public Integer groupFK;

    public Pater() {
    }


    public String getPater() {
        return this.pater;
    }

    public void setPater(String pater) {
        this.pater = pater;
    }
}