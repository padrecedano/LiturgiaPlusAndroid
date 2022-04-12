package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @date 25/3/22
 * @since
 */
public class UserWithPlaylistsAndSongs {
    @Embedded
    public Today user;
    @Relation(
            entity = SalmodiaEntity.class,
            parentColumn = "oficioFK",
            entityColumn = "liturgiaId"
    )
    public List<SalmodiaWithSalmos> salmos;
}
