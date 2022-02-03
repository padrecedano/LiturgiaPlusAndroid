package org.deiverbum.app.core.utils;

/**
 * @author A. Cedano
 * @version 1.0
 * @date 28/11/21
 * @since 2021.01
 */
import java.io.IOException;

public class NoConnectivityException extends IOException {

    @Override
    public String getMessage() {
        return "No network available, please check your WiFi or Data connection";
    }
}
