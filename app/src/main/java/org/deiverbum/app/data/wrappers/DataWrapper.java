package org.deiverbum.app.data.wrappers;

/**
 * Clase envoltorio para manejar las situaciones de error.
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 *
 * @param <T> Instancia de una clase del modelo con los datos
 * @param <E> Instancia de {@link CustomException} con un mensaje de error
 */
public class DataWrapper<T, E extends CustomException> {
    private T data;
    public CustomException error;
    public DataWrapper.Status status;

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

    public void postValue(T data) {
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