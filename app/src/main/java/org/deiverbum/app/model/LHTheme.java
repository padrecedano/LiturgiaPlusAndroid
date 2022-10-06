package org.deiverbum.app.model;

public class LHTheme {
    public Integer themeID=0;
    public String theme="";

    public Integer getThemeID() {
        return themeID;
    }

    public void setThemeID(Integer themeID) {
        this.themeID = themeID;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @SuppressWarnings("unused")
    public LHTheme() {}
}