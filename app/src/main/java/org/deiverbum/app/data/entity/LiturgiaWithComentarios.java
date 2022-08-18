package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Comentario;
import org.deiverbum.app.model.ComentarioBiblico;
import org.deiverbum.app.model.Homilia;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LiturgiaWithComentarios {
/*
    @Embedded
    public MisaLecturaEntity joinEntity;

    @Relation(
            parentColumn = "lecturaFK",
            entityColumn = "readingFK",
            entity = BibleHomilyJoinEntity.class
    )
    public BibleHomilyJoinEntity homiliaa;
*/
    @Embedded
    public BibleHomilyJoinEntity joinEntity;

    @Relation(
            parentColumn = "homiliaId",
            entityColumn = "homilyFK",
            entity = BibleHomilyJoinEntity.class
    )
    public ComentarioWithAll homilia;

    public ComentarioBiblico getDomainModel() {
        ComentarioBiblico dm=null;//homilia.getDomainModel();
        //dm.setTema(joinEntity.getT());
        //dm.setCita(joinEntity.getTema());
        //dm.setTema(joinEntity.getTema());

        return dm;
    }

}
