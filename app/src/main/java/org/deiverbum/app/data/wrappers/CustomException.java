package org.deiverbum.app.data.wrappers;

import java.io.PrintWriter;
import java.io.StringWriter;

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



    public String getTrace() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        this.printStackTrace(pw);
        return sw.toString();
    }


    public CustomException (Throwable cause) {
        super (cause);
    }

    public CustomException (String message, Throwable cause) {
        super (message, cause);
    }
}
