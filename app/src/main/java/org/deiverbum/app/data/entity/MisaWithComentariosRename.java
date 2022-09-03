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
public class MisaWithComentariosRename {
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
    public List<LecturaWithLibro> lecturaList;

    @Relation(
            parentColumn = "readingFK",
            entityColumn = "readingID",
            entity = BibliaLecturaEntity.class
    )
    public LecturaWithLibro lecturaOne;
/*
    @Relation(
            parentColumn = "liturgyFK",
            entityColumn = "liturgiaId",
            entity = LiturgiaEntity.class
    )
    public LiturgiaWithTiempo liturgia;
*/
    public List<BiblicaMisa> getBiblicaMisas() {
        List<BiblicaMisa> theModel=new ArrayList<>();
        for (LecturaWithLibro item : lecturaList) {
            BiblicaMisa bm=item.getDomainModelMisa();
            bm.setOrden(misaLectura.getOrden());
            theModel.add(bm);
        }
return theModel;
        /*BiblicaMisa theModel = lecturaEntity.getDomainModelMisa();
        theModel.setTema(misaLectura.getTema());
        theModel.setOrden(misaLectura.getOrden());
        return theModel;*/
    }

    public BiblicaMisa getBiblicaMisa() {

        BiblicaMisa theModel = lecturaOne.getDomainModelMisa();
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
