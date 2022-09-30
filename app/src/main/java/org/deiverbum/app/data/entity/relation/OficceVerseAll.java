package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LHOficceVerseEntity;
import org.deiverbum.app.data.entity.LHOficceVerseJoinEntity;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class OficceVerseAll {
    @Embedded
    public LHOficceVerseJoinEntity theJoin;
    @Relation(
            parentColumn = "verseFK",
            entityColumn = "verseID",
            entity = LHOficceVerseEntity.class
    )
    public LHOficceVerseEntity theEntity;

    public String getDomainModel(){
        return theEntity.getResponsorio();
    }

}
