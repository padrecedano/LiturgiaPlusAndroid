package org.deiverbum.app.data.wrappers;

/**
 * Clase envoltorio para manejar las situaciones de error.
 *
 * @param <T> Instancia de una clase del modelo con los datos
 * @param <E> Instancia de {@link CustomException} con un mensaje de error
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
public class DataWrapper<T, E extends CustomException> {
    public CustomException error;
    public DataWrapper.Status status;
    private T data;

    public DataWrapper(E e) {
        this.error = e;
        this.status = Status.ERROR;
    }

    public DataWrapper() {

    }

    public DataWrapper(T data) {
        this.data = data;
        this.status = Status.SUCCESS;
    }

    public void setValue(T data) {
        this.status = Status.SUCCESS;
        this.data = data;
    }

    public void setValue(E e) {
        this.error = e;
        this.status = Status.ERROR;
    }

    public void setException(String s) {
        this.error = new CustomException(s);
        this.status = Status.ERROR;
    }

    /**
     * Cambiar por setException
     *
     * @param e La Excepción
     */
    @Deprecated
    public void postValue(E e) {
        this.error = e;
        this.status = Status.ERROR;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error.getMessage();
    }

    public enum Status {SUCCESS, ERROR, LOADING}
}