package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "lh_salmo")
public class SalmoEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "salmoId")
    public Integer salmoId;

    @NonNull
    @ColumnInfo(name = "salmo")
    public String salmo;

    @NonNull
    @ColumnInfo(name = "pericopaId")
    public Integer pericopaId;

    @ColumnInfo(name = "salmoRef")
    public String salmoRef;

    public void setSalmo(String salmo) {
        this.salmo = salmo;
    }

    public String getSalmo() {
        return (salmo != null) ? salmo : "";
    }

    public void setSalmoRef(String salmoRef) {
        this.salmoRef = salmoRef;
    }

    public String getSalmoRef() {
        return (salmoRef != null) ? salmoRef : "";
    }

    public Integer getSalmoId() {
        return salmoId;
    }

    public void setSalmoId(Integer salmoId) {
        this.salmoId = salmoId;
    }

    public Integer getPericopaId() {
        return pericopaId;
    }

    public void setPericopaId(Integer pericopaId) {
        this.pericopaId = pericopaId;
    }
}

