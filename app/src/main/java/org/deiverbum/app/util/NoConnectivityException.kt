package org.deiverbum.app.util

import java.io.IOException

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.01
 */
class NoConnectivityException : IOException() {
    override val message: String
        get() = "No network available, please check your WiFi or Data connection"
}