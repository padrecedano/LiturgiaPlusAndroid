package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.PATER;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.deiverbum.app.model.Pater;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 *
 * CREATE TABLE `padre` (
 * 	`padreId` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
 * 	`padre` TEXT NOT NULL,
 * 	`liturgyName` TEXT NOT NULL,
 * 	`lugarFK` INTEGER NOT NULL DEFAULT 0,
 * 	`tipoFK` INTEGER NOT NULL DEFAULT 0,
 * 	`tituloFK` INTEGER NOT NULL DEFAULT 0,
 * 	`misionFK` INTEGER NOT NULL DEFAULT 0,
 * 	`sexoFK` INTEGER NOT NULL DEFAULT 0,
 * 	`grupoFK` INTEGER NOT NULL DEFAULT 0,
 *     UNIQUE  (`padre`,`lugarFK`,`tipoFK`,`tituloFK`,`misionFK`,`sexoFK`,`grupoFK`)
 *  );
 */

@Entity(tableName = PATER,
        indices={@Index(value={"pater","placeFK","typeFK","titleFK","missionFK","sexFK","groupFK"},unique = true)}
)
public class PaterEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "paterID")
    public Integer padreId=0;

    @NonNull
    @ColumnInfo(name = "pater")
    public String padre="";

    @NonNull
    @ColumnInfo(name = "liturgyName")
    public String liturgyName="";

    @NonNull
    @ColumnInfo(name = "placeFK", defaultValue = "0")
    public Integer lugarFK=0;

    @NonNull
    @ColumnInfo(name = "typeFK", defaultValue = "0")
    public Integer tipoFK=0;

    @NonNull
    @ColumnInfo(name = "titleFK", defaultValue = "0")
    public Integer tituloFK=0;

    @NonNull
    @ColumnInfo(name = "missionFK", defaultValue = "0")
    public Integer misionFK=0;

    @NonNull
    @ColumnInfo(name = "sexFK", defaultValue = "0")
    public Integer sexoFK=0;

    @NonNull
    @ColumnInfo(name = "groupFK", defaultValue = "0")
    public Integer grupoFK=0;

    @NonNull
    public String getPadre() {
        return padre;
    }

    public String getLiturgyName() {
        return ( liturgyName.equals("") ) ?  getPadre() : liturgyName;
    }

    public Pater getDomainModel() {
        Pater dm=new Pater();
        dm.setPater(padre);
        return dm;
    }
}

