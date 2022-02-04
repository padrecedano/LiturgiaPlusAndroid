package org.deiverbum.app.core.utils;
import java.io.IOException;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.01
 */

public class NoConnectivityException extends IOException {

    @Override
    public String getMessage() {
        return "No network available, please check your WiFi or Data connection";
    }
}
