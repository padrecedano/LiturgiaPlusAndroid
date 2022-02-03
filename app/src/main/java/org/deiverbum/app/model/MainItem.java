package org.deiverbum.app.model;

public class MainItem {
    private String name;
    private int itemId;
    private int thumbnail;
    private int color;

    public MainItem() {
    }

    public MainItem(String name, int itemId, int thumbnail, int color) {
        this.name = name;
        this.itemId = itemId;
        this.thumbnail = thumbnail;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}