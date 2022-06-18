package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 *
 * CREATE TABLE `obra` (
 * 	`obraId` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
 * 	`obra` TEXT NOT NULL,
 * 	`liturgyName` TEXT NOT NULL DEFAULT '',
 * 	`subTitulo` TEXT DEFAULT NULL,
 * 	`fecha` INTEGER DEFAULT NULL,
 * 	`volumen` INTEGER DEFAULT NULL,
 * 	`editorial` TEXT DEFAULT NULL,
 * 	`ciudad` TEXT DEFAULT NULL,
 * 	`year` INTEGER DEFAULT NULL,
 * 	`padreFK` INTEGER NOT NULL DEFAULT 0,
 * 	`tipoFK` INTEGER NOT NULL DEFAULT 0,
 * 	`coleccionFK` INTEGER NOT NULL DEFAULT 0,
 * 	FOREIGN KEY(`padreFK`) REFERENCES `padre`(`padreId`) ON DELETE CASCADE ON UPDATE CASCADE,
 * 	UNIQUE (`obra`, `padreFK`, `volumen`)
 *  );
 */

@Entity(tableName = "obra",
        foreignKeys =
                {
                        @ForeignKey(
                                entity = PadreEntity.class,
                                parentColumns = "padreId",
                                childColumns = "padreFK",
                                onDelete = ForeignKey.CASCADE,
                                onUpdate = ForeignKey.CASCADE)},
         indices = {@Index(value = {"obra","padreFK","volumen"},unique = true)}
)
public class ObraEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "obraId")
    public Integer obraId=0;

    @NonNull
    @ColumnInfo(name = "obra")
    public String obra="";

    @NonNull
    @ColumnInfo(name = "liturgyName", defaultValue = "")
    public String liturgyName="";

    //@NonNull
    @ColumnInfo(name = "subTitulo", defaultValue = "NULL")
    public String subTitulo="";

    //@NonNull
    @ColumnInfo(name = "volumen", defaultValue = "NULL")
    public Integer volumen=0;

    @ColumnInfo(name = "fecha", defaultValue = "NULL")
    public Integer fecha=0;

    //@NonNull
    @ColumnInfo(name = "editorial", defaultValue = "NULL")
    public String editorial="";

    //@NonNull
    @ColumnInfo(name = "ciudad", defaultValue = "NULL")
    public String ciudad;

    //@NonNull
    @ColumnInfo(name = "year", defaultValue = "NULL")
    public Integer year;

    @NonNull
    @ColumnInfo(name = "padreFK", defaultValue = "0")
    public Integer padreFK=0;

    @NonNull
    @ColumnInfo(name = "tipoFK", defaultValue = "0")
    public Integer tipoFK=0;

    @NonNull
    @ColumnInfo(name = "coleccionFK", defaultValue = "0")
    public Integer coleccionFK=0;

    public String getObra() {
        return obra;
    }
}

