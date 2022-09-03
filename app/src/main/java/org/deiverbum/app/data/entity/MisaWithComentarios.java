package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaMisa;
import org.deiverbum.app.model.ComentarioBiblico;
import org.deiverbum.app.model.Liturgia;

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
            entity = BibliaLecturaEntity.class
    )
    public BibliaLecturaEntity lecturaEntity;

/*
    @Relation(
            parentColumn = "liturgyFK",
            entityColumn = "liturgiaId",
            entity = LiturgiaEntity.class
    )
    public LiturgiaWithTiempo liturgia;
*/
    public BiblicaMisa getBiblicaMisa() {
        BiblicaMisa theModel = lecturaEntity.getDomainModelMisa();
        theModel.setTema(misaLectura.getTema());
        theModel.setOrden(misaLectura.getOrden());
        return theModel;
    }

    public Liturgia getLiturgia() {
        return null;//liturgia.getDomainModel();
    }

    public List<ComentarioBiblico> getDomainModel() {
        List<ComentarioBiblico> listModel = new ArrayList<>();
        //lectura.
        if (lectura.size() > 0) {
            for (BibleHomilyWithAll item : lectura) {
                //if(homilias.)
                //item.getDomainModel().getOrden();
                ComentarioBiblico theModel = item.getDomainModel();
                BiblicaMisa biblica = getBiblicaMisa();//misaLectura.getDomainModel();
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
