package org.deiverbum.app.utils;

/**
 * @author A. Cedano
 * @version 1.0
 * @date 28/11/21
 * @since 2021.01
 */
import androidx.lifecycle.Observer;

public class EventObserver<T> implements Observer<Event<T>> {

    private EventObserverCallback<T> eventObserverCallback;
    public EventObserver(EventObserverCallback<T> observerCallback){
        this.eventObserverCallback = observerCallback;
    }

    @Override
    public void onChanged(Event<T> tEvent) {
        T content  = tEvent.getContentIfNotHandled();
        if (content != null){
            eventObserverCallback.onEventUnhandledContent(content);
        }
    }
}
