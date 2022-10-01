package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.BibleHomilyJoinEntity;
import org.deiverbum.app.data.entity.BibleHomilyThemeEntity;
import org.deiverbum.app.data.entity.HomilyEntity;
import org.deiverbum.app.model.BibleComment;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class BibleHomilyWithAll {
    @Embedded
    public BibleHomilyJoinEntity bibliaLecturaEntity;

    @Relation(
            parentColumn = "homilyFK",
            entityColumn = "homilyFK",
            entity = BibleHomilyThemeEntity.class
    )

    public BibleHomilyThemeEntity themeEntity;

    @Relation(
            parentColumn = "homilyFK",
            entityColumn = "homilyID",
            entity = HomilyEntity.class
    )

    public HomilyAll homilia;


    public BibleComment getDomainModel() {
        BibleComment theModel=new BibleComment();
        if(themeEntity!=null) {
            theModel.setCita(themeEntity.getBiblical());
            theModel.setTema(themeEntity.getTheological());
            theModel.setRef(themeEntity.getReference());
        }
        theModel.setPadre(homilia.paterOpusAll.getPaterEntity());
        theModel.setObra(homilia.paterOpusAll.paterOpusEntity.getOpusName());
        theModel.setTexto(homilia.homilia.getTexto());
        theModel.setFecha(String.valueOf(homilia.homilia.fecha));

        return theModel;
    }


}
