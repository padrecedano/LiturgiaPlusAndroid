package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class SalmodiaWithSalmos {
    @Embedded
    public SalmodiaEntity salmodia;
    @Relation(
            parentColumn = "salmoFK",
            entityColumn = "salmoId",
            entity = SalmoEntity.class/*,
            associateBy = @Junction(SalmodiaSalmoCrossRef.class)*/
    )
    public SalmoEntity salmo;

    @Relation(
            parentColumn = "antifonaFK",
            entityColumn = "antifonaId",
            entity = AntifonaEntity.class/*,
            associateBy = @Junction(SalmodiaSalmoCrossRef.class)*/
    )
    public AntifonaEntity antifonaEntity;

    @Relation(
            parentColumn = "temaFK",
            entityColumn = "temaId",
            entity = TemaEntity.class/*,
            associateBy = @Junction(SalmodiaSalmoCrossRef.class)*/
    )
    public TemaEntity tema;

    @Relation(
            parentColumn = "epigrafeFK",
            entityColumn = "epigrafeId",
            entity = EpigrafeEntity.class/*,
            associateBy = @Junction(SalmodiaSalmoCrossRef.class)*/
    )
    public EpigrafeEntity epigrafe;


}
