package org.deiverbum.app.data.entity.relation;

import android.util.Log;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.HomilyEntity;
import org.deiverbum.app.data.entity.LHOfficePatristicEntity;
import org.deiverbum.app.data.entity.LHResponsoryEntity;
import org.deiverbum.app.model.LHOfficePatristic;

/**
 * <p>Obtiene los valores para una lectura bíblica de
 * la Liturgia de las Horas,
 * desde las distintas tablas relacionadas.</p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class PatristicaOficioWithResponsorio {
    @Embedded
    public LHOfficePatristicEntity lhPatristica;

    @Relation(
            parentColumn = "homilyFK",
            entityColumn = "homilyID",
            entity = HomilyEntity.class
    )
    public HomilyAll homilyAll;

    @Relation(
            parentColumn = "responsoryFK",
            entityColumn = "responsoryID",
            entity = LHResponsoryEntity.class
    )
    public LHResponsoryEntity lhResponsorio;

    public LHOfficePatristic getDomainModelOficio(Integer tiempoId) {
        try {
            LHOfficePatristic theModel = homilyAll.getPatristicaDomainModel();
            theModel.setTheme(lhPatristica.tema);
            theModel.setSource(lhPatristica.fuente);
            theModel.setTheOrder(lhPatristica.orden);
            theModel.setResponsorioLargo(lhResponsorio.getDomainModel(tiempoId));
            return theModel;
        } catch (Exception e) {
            Log.e("ERR", e.getMessage());
        }
        return null;
    }
}
