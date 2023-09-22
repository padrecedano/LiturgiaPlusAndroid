package org.deiverbum.app.util

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
interface TextToSpeechCallback {
    fun onStart()
    fun onCompleted()
    fun onError()
}