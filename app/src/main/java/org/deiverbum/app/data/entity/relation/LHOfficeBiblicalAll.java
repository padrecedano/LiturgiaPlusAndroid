package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LHOfficeBiblicalEasterEntity;
import org.deiverbum.app.data.entity.LHOfficeBiblicalEntity;
import org.deiverbum.app.data.entity.LHOfficeBiblicalJoinEntity;
import org.deiverbum.app.model.LHOfficeBiblical;

import java.util.ArrayList;
import java.util.List;

/**
 *  <p>Obtiene los valores para una lectura bíblica de
 *  la Liturgy de las Horas,
 *  desde las distintas tablas relacionadas.</p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class LHOfficeBiblicalAll {
    @Embedded
    public LHOfficeBiblicalJoinEntity lhBiblica;

    @Relation(
            parentColumn = "groupID",
            entityColumn = "groupFK",
            entity = LHOfficeBiblicalEntity.class
    )
    public List<LHOfficeBiblicalWithResponsory> biblica;

    @Relation(
            parentColumn = "groupID",
            entityColumn = "groupFK",
            entity = LHOfficeBiblicalEasterEntity.class
    )
    public List<LHOfficeBiblicalEasterEntity> biblicaE;

    public List<LHOfficeBiblical> getDomainModel(Integer tiempoId) {
        final List<LHOfficeBiblical> theList = new ArrayList<>();

        for (LHOfficeBiblicalWithResponsory item : biblica) {
            theList.add(item.getDomainModel(tiempoId));
        }
        return theList;
    }

}
