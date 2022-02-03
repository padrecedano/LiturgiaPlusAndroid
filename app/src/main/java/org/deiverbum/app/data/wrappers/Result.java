package org.deiverbum.app.data.wrappers;

/**
 * @author A. Cedano
 * @version 1.0
 * @date 27/11/21
 * @since
 */
// Result.java
public abstract class Result<T> {
    private Result() {}

    public static final class Success<T> extends Result<T> {
        public T data;

        public Success(T data) {
            this.data = data;
        }
    }

    public static final class Error<T> extends Result<T> {
        public Exception exception;

        public Error(Exception exception) {
            this.exception = exception;
        }
    }
}

// LoginRepository.java



