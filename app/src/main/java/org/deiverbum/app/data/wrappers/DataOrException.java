package org.deiverbum.app.data.wrappers;


public class DataOrException<T> {
    private T data;
    private Exception exception;

    public void setData(T data) {
        this.data = data;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public T getData() {
        return data;
    }

}
