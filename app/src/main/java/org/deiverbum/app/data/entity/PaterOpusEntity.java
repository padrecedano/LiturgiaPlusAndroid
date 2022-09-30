package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.PATER_OPUS;

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

@Entity(tableName = PATER_OPUS,
        foreignKeys =
                {
                        @ForeignKey(
                                entity = PaterEntity.class,
                                parentColumns = "paterID",
                                childColumns = "paterFK",
                                onDelete = ForeignKey.CASCADE,
                                onUpdate = ForeignKey.CASCADE)},
         indices = {@Index(value = {"opus","paterFK","volume"},unique = true)}
)
public class PaterOpusEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "opusID")
    public Integer obraId=0;

    @NonNull
    @ColumnInfo(name = "opus")
    public String obra="";

    @NonNull
    @ColumnInfo(name = "liturgyName", defaultValue = "")
    public String liturgyName="";

    //@NonNull
    @ColumnInfo(name = "subTitle", defaultValue = "NULL")
    public String subTitulo="";

    //@NonNull
    @ColumnInfo(name = "volume", defaultValue = "NULL")
    public Integer volumen=0;

    @ColumnInfo(name = "date", defaultValue = "NULL")
    public Integer fecha=0;

    //@NonNull
    @ColumnInfo(name = "editorial", defaultValue = "NULL")
    public String editorial="";

    //@NonNull
    @ColumnInfo(name = "city", defaultValue = "NULL")
    public String ciudad;

    //@NonNull
    @ColumnInfo(name = "year", defaultValue = "NULL")
    public Integer year;

    @NonNull
    @ColumnInfo(name = "paterFK", defaultValue = "0")
    public Integer padreFK=0;

    @NonNull
    @ColumnInfo(name = "typeFK", defaultValue = "0")
    public Integer tipoFK=0;

    @NonNull
    @ColumnInfo(name = "collectionFK", defaultValue = "0")
    public Integer coleccionFK=0;

    @NonNull
    public String getObra() {
        return obra;
    }
}

