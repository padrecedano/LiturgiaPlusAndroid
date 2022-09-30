package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LHInvitatoryEntity;
import org.deiverbum.app.data.entity.LHInvitatoryJoinEntity;
import org.deiverbum.app.data.entity.PsalmEntity;

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

    @SuppressWarnings("SameReturnValue")
    public String getAntifona() {
        return "antifona.getAntifona();";
    }
}
