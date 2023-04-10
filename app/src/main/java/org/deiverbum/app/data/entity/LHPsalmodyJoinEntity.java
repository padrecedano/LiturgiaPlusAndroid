package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LH_PSALMODY_JOIN;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Entity(tableName = LH_PSALMODY_JOIN,
        indices = {@Index(value = {"groupID", "type"}, unique = true)}
)
public class LHPsalmodyJoinEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "groupID")
    public Integer grupoId = 0;

    @NonNull
    @ColumnInfo(name = "type")
    public Integer tipo = 0;

    public int getTipo() {
        return tipo;
    }
}

