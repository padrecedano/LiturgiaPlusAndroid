package org.deiverbum.app.data.adapters;

public class OracionItem {
    public int id;
    public String title;
    public String description;
    public int navId;

    public OracionItem(int id, String title, String description,  int navId) {
        this.id=id;
        this.title = title;
        this.description=description;
        this.navId=navId;
    }

}
