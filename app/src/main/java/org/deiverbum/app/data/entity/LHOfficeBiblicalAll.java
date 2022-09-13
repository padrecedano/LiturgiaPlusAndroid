package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaOficio;

import java.util.ArrayList;
import java.util.List;

/**
 *  <p>Obtiene los valores para una lectura bíblica de
 *  la Liturgia de las Horas,
 *  desde las distintas tablas relacionadas.</p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
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

    public List<BiblicaOficio> getDomainModel(Integer tiempoId) {
        final List<BiblicaOficio> theList = new ArrayList<>();

        for (LHOfficeBiblicalWithResponsory item : biblica) {
            theList.add(item.getDomainModel(tiempoId));
        }
        return theList;
    }

}
