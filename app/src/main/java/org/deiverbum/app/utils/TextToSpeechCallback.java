package org.deiverbum.app.utils;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */

public interface TextToSpeechCallback {
    void onStart();

    void onCompleted();

    void onError();
}
