package org.deiverbum.app.data.adapters;

public class OracionItem {
    public final int id;
    public final String title;
    public final String description;
    public final int navId;

    public OracionItem(int id, String title, String description,  int navId) {
        this.id=id;
        this.title = title;
        this.description=description;
        this.navId=navId;
    }

}
