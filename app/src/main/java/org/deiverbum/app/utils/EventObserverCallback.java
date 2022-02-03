package org.deiverbum.app.utils;

/**
 * @author A. Cedano
 * @version 1.0
 * @date 28/11/21
 * @since 2021.01
 */
public interface EventObserverCallback<T> {
    void onEventUnhandledContent(T t);
}