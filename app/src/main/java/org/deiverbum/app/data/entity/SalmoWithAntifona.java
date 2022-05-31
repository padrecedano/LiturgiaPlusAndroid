package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class SalmoWithAntifona {
    @Embedded
    public SalmoEntity obra;
    @Relation(
            parentColumn = "salmoFK",
            entityColumn = "salmoId",
            entity = PadreEntity.class/*,
            associateBy = @Junction(SalmodiaSalmoCrossRef.class)*/
    )
    public PadreEntity padre;

    public String getPadre(){
        return padre.getPadre();
    }


}
