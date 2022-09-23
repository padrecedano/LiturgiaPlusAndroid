package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.MassReading;
import org.deiverbum.app.model.BibleComment;
import org.deiverbum.app.model.Liturgy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class MisaWithComentarios {
    @Embedded
    /*public LiturgyGroupEntity misaLectura;

    @Relation(
            parentColumn = "groupID",
            entityColumn = "groupFK",
            entity = MassReadingEntity.class
    )*/

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
    public BibleReadingEntity lecturaEntity;

/*
    @Relation(
            parentColumn = "liturgyFK",
            entityColumn = "liturgiaId",
            entity = LiturgyEntity.class
    )
    public LiturgyWithTime liturgia;
*/
    public MassReading getBiblicaMisa() {
        MassReading theModel = lecturaEntity.getDomainModelMisa();
        theModel.setTema(misaLectura.getTema());
        theModel.setOrden(misaLectura.getOrden());
        return theModel;
    }

    public Liturgy getLiturgia() {
        return null;//liturgia.getDomainModel();
    }

    public List<BibleComment> getDomainModel() {
        List<BibleComment> listModel = new ArrayList<>();
        //lectura.
        if (lectura.size() > 0) {
            for (BibleHomilyWithAll item : lectura) {
                //if(homilias.)
                //item.getDomainModel().getOrden();
                BibleComment theModel = item.getDomainModel();
                MassReading biblica = getBiblicaMisa();//misaLectura.getDomainModel();
                biblica.setOrden(misaLectura.getOrden());
                theModel.setBiblica(biblica);
                if (item != null) {
                    listModel.add(theModel);
                }
            }
        }
        return listModel;
    }


}
