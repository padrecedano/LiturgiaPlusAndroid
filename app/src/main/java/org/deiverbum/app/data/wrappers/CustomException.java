package org.deiverbum.app.data.wrappers;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
public class CustomException extends Exception {

    public CustomException () {
    }

    public CustomException (String message) {
        super (message);
    }

    public CustomException (Throwable cause) {
        super (cause);
    }

    public CustomException (String message, Throwable cause) {
        super (message, cause);
    }
}