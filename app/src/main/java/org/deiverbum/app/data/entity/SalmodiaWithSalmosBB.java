package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class SalmodiaWithSalmosBB {
    @Embedded
    public BibliaLecturaEntity salmodia;
    @Relation(
            parentColumn = "libroId",
            entityColumn = "libroId",
            entity = BibliaLibroEntity.class
    )
    public BibliaLibroEntity salmodiaEntity;




}
