package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.ComentarioBiblico;
import org.deiverbum.app.model.Patristica;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class ComentarioWithAll {
    @Embedded
    public LiturgyGroupEntity groupEntity;

    @Relation(
            parentColumn = "groupID",
            entityColumn = "groupFK",
            entity = MassReadingEntity.class
    )

    public MassReadingEntity joinEntity;

/*
    @Relation(
            parentColumn = "homilyFK",
            entityColumn = "homilyFK",
            entity = BibleHomilyJoinEntity.class
    )
    public BibleHomilyJoinEntity themeEntity1;
*/
    @Relation(
            parentColumn = "homilyFK",
            entityColumn = "homilyFK",
            entity = BibleHomilyThemeEntity.class
    )
    public BibleHomilyThemeEntity themeEntity;

    @Relation(
            parentColumn = "homilyFK",
            entityColumn = "homiliaId",
            entity = HomiliaEntity.class
    )
    public HomiliaWithAll homilia;

    public ComentarioBiblico getDomainModel() {
        ComentarioBiblico theModel=new ComentarioBiblico();
        if(themeEntity!=null) {
            theModel.setCita(themeEntity.getBiblical());
            theModel.setTema(themeEntity.getTheological());
            theModel.setRef(themeEntity.getReference());
        }
        theModel.setPadre(homilia.obraWithPadre.getPadre());
        theModel.setObra(homilia.obraWithPadre.obra.getObra());
        theModel.setTexto(homilia.homilia.getTexto());
        theModel.setFecha(String.valueOf(homilia.homilia.fecha));

        return theModel;
    }

}
