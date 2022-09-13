package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LHInvitatoryWithAntiphon {
    @Embedded
    public LHInvitatoryEntity invitatorio;

    @Relation(
            parentColumn = "casoId",
            entityColumn = "casoFK",
            entity = LHInvitatoryJoinEntity.class
    )
    public LHInvitatoryJoinEntity invitatorioEntity;

    @Relation(
            parentColumn = "salmoFK",
            entityColumn = "salmoId",
            entity = PsalmEntity.class
    )
    public PsalmEntity salmo;

    public String getAntifona() {
        return "antifona.getAntifona();";
    }
}
