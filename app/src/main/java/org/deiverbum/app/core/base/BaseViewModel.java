package org.deiverbum.app.core.base;

/**
 * @author A. Cedano
 * @version 1.0
 * @date 28/11/21
 * @since 2021.01
 */
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public abstract class BaseViewModel extends ViewModel {

    public CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
