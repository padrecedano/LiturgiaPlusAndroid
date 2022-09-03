package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class ObraWithPadre {
    @Embedded
    public ObraEntity obra;
    @Relation(
            parentColumn = "paterFK",
            entityColumn = "paterID",
            entity = PadreEntity.class
    )
    public PadreEntity padre;

    public String getPadre(){
        return padre.getLiturgyName();
    }


}
