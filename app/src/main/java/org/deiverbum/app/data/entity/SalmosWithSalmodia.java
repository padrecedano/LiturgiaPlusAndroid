package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @date 20/3/22
 * @since
 */
public class SalmosWithSalmodia {

    @Embedded
    public SalmodiaEntity salmodia;

    @Relation(
            parentColumn = "salmodiaId",
            entityColumn = "salmoId",
            associateBy = @Junction(SalmodiaSalmoCrossRef.class)
    )
    public SalmoEntity salmoId;
    //public List<Salmo> salmos;


}
