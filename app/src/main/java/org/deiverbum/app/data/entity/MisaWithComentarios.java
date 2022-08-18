package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.BiblicaMisa;
import org.deiverbum.app.model.ComentarioBiblico;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class MisaWithComentarios {
    @Embedded
    public MisaLecturaEntity misaLectura;

    @Relation(
            parentColumn = "lecturaFK",
            entityColumn = "lecturaId",
            entity = BibliaLecturaEntity.class
    )
    public MisaLecturaWithLibro lectura;


    @Relation(
            parentColumn = "lecturaFK",
            entityColumn = "readingFK",
            entity = BibleHomilyJoinEntity.class
    )
    public List<ComentarioWithAll> lecturaa;

    public List<ComentarioBiblico> getDomainModel() {
        List<ComentarioBiblico> listModel = new ArrayList<>();
        //lectura.
        if (lecturaa.size() > 0) {
            for (ComentarioWithAll item : lecturaa) {
                //if(homilias.)
                //item.getDomainModel().getOrden();
                ComentarioBiblico theModel = item.getDomainModel();
                BiblicaMisa biblica = lectura.getDomainModel();
                biblica.setOrden(misaLectura.getOrden());
                theModel.setBiblica(biblica);
                if (item != null) {
                    listModel.add(theModel);
                }
            }
        }
        return listModel;
    }

    public BiblicaMisa getDomainModels() {
        BiblicaMisa theModel = lectura.getDomainModelMisa();
        theModel.setTema(misaLectura.getTema());
        theModel.setOrden(misaLectura.getOrden());
        return theModel;
    }

}
