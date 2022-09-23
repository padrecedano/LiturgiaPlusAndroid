package org.deiverbum.app.data.wrappers;

import org.deiverbum.app.model.HomilyList;

import java.util.List;

/**
 * Clase envoltorio para manejar las situaciones de error.
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 *
 * @param <T> Instancia de una clase del modelo con los datos
 * //@param <E> Instancia de {@link CustomException} con un mensaje de error
 */
public class CrudWrapper<T> {
    private T datas;
    public Crud crud;
    public List<HomilyList> data;
    private List<T> create;
    private List<T> read;
    private List<T> update;
    private List<T> delete;


    public CrudWrapper.Status status;
/*
    public CrudWrapper(E e) {
        this.crud = (Crud) e;
        this.status = Status.ERROR;
    }
*/
    public CrudWrapper() {

    }

    public CrudWrapper(T data) {
        this.datas = data;
        this.status = Status.SUCCESS;
    }

    public void postValue(T data) {
        this.datas = data;
        this.status = Status.SUCCESS;
    }

    public void setCreate(List<T> data) {
        this.create = data;
        this.status = Status.SUCCESS;
    }

    public void setValue(T data) {
        this.status = Status.SUCCESS;
        this.datas = data;
    }
/*
    public void setValue(E e) {
        this.error = e;
        this.status = Status.ERROR;
    }

    public void setException(String s) {
        this.error = new CustomException(s);
        this.status = Status.ERROR;
    }
*/
    /**
     * Cambiar por setException
     */
/*    @Deprecated
    public void postValue(E e) {
        this.error = e;
        this.status = Status.ERROR;
    }
*/
    public T getData() {
        return datas;
    }

    /*public String getError() {
        return error.getMessage();
    }*/

    public enum Status {SUCCESS, ERROR, LOADING}

}