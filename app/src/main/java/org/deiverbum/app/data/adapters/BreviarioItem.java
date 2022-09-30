package org.deiverbum.app.data.adapters;

public class BreviarioItem {
    public final String text;
    public final String letra;
    public final int color;
    public final int navId;

    public BreviarioItem(String t, int c, String l, int navId) {
        text = t;
        color = c;
        letra=l;
        this.navId=navId;
    }

}
