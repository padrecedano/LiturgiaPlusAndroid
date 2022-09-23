package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BibleCommentList;
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
            entity = BibleReadingEntity.class
    )
    public List<BibleReadingWithBook> lecturaList;

    @Relation(
            parentColumn = "readingFK",
            entityColumn = "readingID",
            entity = BibleReadingEntity.class
    )
    public BibleReadingWithBook lecturaOne;
/*
    @Relation(
            parentColumn = "liturgyFK",
            entityColumn = "liturgiaId",
            entity = LiturgyEntity.class
    )
    public LiturgyWithTime liturgia;
*/
    public List<MassReading> getBiblicaMisas() {
        List<MassReading> theModel=new ArrayList<>();
        for (BibleReadingWithBook item : lecturaList) {
            MassReading bm=item.getDomainModelMisa();
            bm.setOrden(misaLectura.getOrden());
            theModel.add(bm);
        }
return theModel;
        /*MassReading theModel = lecturaEntity.getDomainModelMisa();
        theModel.setTema(misaLectura.getTema());
        theModel.setOrden(misaLectura.getOrden());
        return theModel;*/
    }

    public MassReading getBiblicaMisa() {

        MassReading theModel = lecturaOne.getDomainModelMisa();
        //theModel.bookFK=l
        theModel.setTema(misaLectura.getTema());
        theModel.setOrden(misaLectura.getOrden());
        return theModel;
    }




    public Liturgy getLiturgia() {
        return null;//liturgia.getDomainModel();
    }

    public BibleCommentList getDomainModelN() {
        BibleCommentList dm=new BibleCommentList();
        List<List<BibleComment>> group = new ArrayList<>();
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
            group.add(listModel);
        }
        dm.allComentarios=group;
        return dm;
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
                //biblica.bookFK=
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
