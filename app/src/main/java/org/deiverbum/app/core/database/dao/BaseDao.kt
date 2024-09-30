package org.deiverbum.app.core.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

/**
 * Interfaz base para insertar, actualizar o borrar registros de cualquier tabla.
 *
 * @author A. Cedano
 * @since 2024.1
 *
 * @param [T] la instancia del objeto con cuyos datos se va a operar.
 */
interface BaseDao<T> {

    /**
     * Inserta un objeto en la base de datos.
     *
     * @param obj el objeto a insertar.
     */
    @Insert
    fun insert(obj: T)

    /**
     * Inserta un array of objetos en la base de datos.
     *
     * @param obj los objetos a insertar.
     */
    @Insert
    fun insert(vararg obj: List<T>)

    /**
     * Actualiza un objeto en la base de datos.
     *
     * @param obj el objeto a actualizar.
     */
    @Update
    fun update(obj: T)

    /**
     * Actualiza un array of objetos en la base de datos.
     *
     * @param obj los objetos a actualizar.
     */
    @Insert
    fun update(vararg obj: List<T>)

    /**
     * Borra un objeto en la base de datos.
     *
     * @param obj el objeto a borrar.
     */
    @Delete
    fun delete(obj: T)

    /**
     * Borra un array of objetos en la base de datos.
     *
     * @param obj los objetos a borrar.
     */
    @Insert
    fun delete(vararg obj: List<T>)
}