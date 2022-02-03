package org.deiverbum.app.data.adapters;

public class BreviarioItem {
    public String text;
    public String letra;
    public int color;
    public int navId;

    public BreviarioItem(String t, int c, String l, int navId) {
        text = t;
        color = c;
        letra=l;
        this.navId=navId;
    }

}
