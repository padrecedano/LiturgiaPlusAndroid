package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.deiverbum.app.model.Salmo;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "lh_psalm")
public class SalmoEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "psalmID")
    public Integer salmoId=0;

    @NonNull
    @ColumnInfo(name = "psalm")
    public String salmo="";

    @NonNull
    @ColumnInfo(name = "readingID")
    public Integer pericopaId=0;

    @ColumnInfo(name = "quote")
    public String salmoRef;

    public void setSalmo(@NonNull String salmo) {
        this.salmo = salmo;
    }

    @NonNull
    public String getSalmo() {
        return salmo;
    }

    @SuppressWarnings("unused")
    public void setSalmoRef(String salmoRef) {
        this.salmoRef = salmoRef;
    }

    public String getSalmoRef() {
        return (salmoRef != null) ? salmoRef : "";
    }

    @SuppressWarnings("unused")
    @NonNull
    public Integer getSalmoId() {
        return salmoId;
    }

    @SuppressWarnings("unused")
    public void setSalmoId(@NonNull Integer salmoId) {
        this.salmoId = salmoId;
    }

    @NonNull
    @SuppressWarnings("unused")
    public Integer getPericopaId() {
        return pericopaId;
    }

    @SuppressWarnings("unused")
    public void setPericopaId(@NonNull Integer pericopaId) {
        this.pericopaId = pericopaId;
    }

    public Salmo getDomainModel(){
        Salmo dm=new Salmo();
        dm.setSalmo(getSalmo());
        dm.setRef(getSalmoRef());
        return dm;
    }
}

