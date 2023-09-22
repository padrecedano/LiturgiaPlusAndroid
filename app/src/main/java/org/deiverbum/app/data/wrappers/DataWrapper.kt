package org.deiverbum.app.data.wrappers

/**
 * Clase envoltorio para manejar las situaciones de error.
 *
 * @param <T> Instancia de una clase del modelo con los datos
 * @param <E> Instancia de [CustomException] con un mensaje de error
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
</E></T> */
class DataWrapper<T, E : CustomException?> {
    var error: CustomException? = null
    private var status: Status? = null
    var data: T? = null
        private set

    fun setValue(data: T) {
        status = Status.SUCCESS
        this.data = data
    }

    fun setValue(e: E) {
        error = e
        status = Status.ERROR
    }


    /**
     * Cambiar por setException
     *
     * @param e La Excepción
     */
    @Deprecated("")
    fun postValue(e: E) {
        error = e
        status = Status.ERROR
    }


    enum class Status {
        SUCCESS, ERROR
    }
}