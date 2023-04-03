package org.deiverbum.app.model;

import org.deiverbum.app.ui.fragments.HomeFragment;

/**
 * <p>
 *     HomeItem es la clase para manejar las opciones
 *     que aparecen en la pantalla inicial de la aplicación
 *     desde un elemento <code>RecyclerView</code>.
 * </p>
 * <p>Esta clase trabaja con:</p>
 *     <ul>
 *         <li>{@link HomeFragment}</li>
 *         <li><code>{@link org.deiverbum.app.data.adapters.HomeAdapter}</code></li>
 *     </ul>
 *
 * @author      A. Cedano
 * @version     1.0
 * @since       2022.1
 */
public class HomeItem {
    private String name;
    private int itemId;
    private int thumbnail;
    private int color;
    private int navId;
    private int imageColor;
    @SuppressWarnings("unused")
    public HomeItem() {
    }

    public HomeItem(String name, int itemId, int thumbnail, int color, int navId, int imageColor) {
        this.name = name;
        this.itemId = itemId;
        this.thumbnail = thumbnail;
        this.imageColor = imageColor;
        this.color = color;
        this.navId = navId;

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

    @SuppressWarnings("unused")
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    @SuppressWarnings("unused")
    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getColor() {
        return color;
    }
    public int getImageColor() {
        return imageColor;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getNavId() {
        return navId;
    }

    @SuppressWarnings("unused")
    public void setNavId(int navId) {
        this.navId = navId;
    }
}