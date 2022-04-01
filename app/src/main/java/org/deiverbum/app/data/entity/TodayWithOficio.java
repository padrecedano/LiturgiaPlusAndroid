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
public class TodayWithOficio {

    @Embedded
    //public Today today;
    public OficioEntity oficio;

    @Relation(
            parentColumn = "oficioId",
            entityColumn = "oficioFK"/*,
            associateBy = @Junction(SalmodiaSalmoCrossRef.class)*/
    )
    //public OficioEntity oficio;
    //public List<Salmo> salmos;
    public Today today;

    @Relation(
            entity = SalmodiaEntity.class,
            parentColumn = "salmodiaFK",
            entityColumn = "salmodiaId" //liturgiaId
    )
    public List<SalmodiaWithSalmos> salmodia;


    @Relation(
            entity = HimnoEntity.class,
            parentColumn = "himnoFK",
            entityColumn = "himnoId" //liturgiaId
    )
    public HimnoEntity himno;

    @Relation(
            entity = SantoEntity.class,
            parentColumn = "santoFK",
            entityColumn = "santoId" //liturgiaId
    )
    public SantoEntity santo;


}
