package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class PaterOpusAll {
    @Embedded
    public PaterOpusEntity obra;
    @Relation(
            parentColumn = "paterFK",
            entityColumn = "paterID",
            entity = PaterEntity.class
    )
    public PaterEntity padre;

    public String getPadre(){
        return padre.getLiturgyName();
    }


}
