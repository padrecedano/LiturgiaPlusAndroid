package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.BibleHomilyJoinEntity;
import org.deiverbum.app.data.entity.BibleReadingEntity;
import org.deiverbum.app.data.entity.MassReadingEntity;
import org.deiverbum.app.model.BibleComment;
import org.deiverbum.app.model.MassReading;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class MisaWithComentarios {

    @Embedded
    public MassReadingEntity misaLectura;

    @Relation(
            parentColumn = "readingFK",
            entityColumn = "readingFK",
            entity = BibleHomilyJoinEntity.class
    )
    public List<BibleHomilyWithAll> lectura;

    @Relation(
            parentColumn = "readingFK",
            entityColumn = "readingID",
            entity = BibleReadingEntity.class
    )
    public BibleReadingWithBook lecturaOne;


    public MassReading getBiblicaMisa() {
        MassReading theModel = lecturaOne.getDomainModelMisa();
        theModel.setTema(misaLectura.getTema());
        theModel.setOrden(misaLectura.getOrden());
        return theModel;
    }

    public List<BibleComment> getDomainModel() {
        List<BibleComment> listModel = new ArrayList<>();
        if (lectura.size() > 0) {
            for (BibleHomilyWithAll item : lectura) {
                BibleComment theModel = item.getDomainModel();
                MassReading biblica = getBiblicaMisa();
                biblica.setOrden(misaLectura.getOrden());
                theModel.setBiblica(biblica);
                listModel.add(theModel);
            }
        }
        return listModel;
    }

}
