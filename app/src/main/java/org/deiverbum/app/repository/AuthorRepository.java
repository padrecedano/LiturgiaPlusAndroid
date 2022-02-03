package org.deiverbum.app.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.deiverbum.app.R;
import org.deiverbum.app.data.ResourcesProvider;
import org.deiverbum.app.utils.Utils;

import java.io.InputStream;

import javax.inject.Inject;

/**
 * Created by a. Cedano on 2021/11/11
 */
public class AuthorRepository {
    private MutableLiveData<String> mText;
    private ResourcesProvider mResourcesProvider;

    @Inject
    public AuthorRepository(ResourcesProvider resourcesProvider) {
        mText = new MutableLiveData<>();
        mResourcesProvider = resourcesProvider;
    }

    public LiveData<String> getText(Context context) {
        int rawId = R.raw.privacy_201902;
        try {
            InputStream in_s = context.getResources().openRawResource(rawId);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            mText.setValue(new String(b));
            in_s.close();
        } catch (Exception e) {
            mText.setValue( String.format("Error: <br>%s",e.getMessage()));
        }
        return mText;
    }

    public LiveData<String> getTextR() {
        mText.setValue(mResourcesProvider.getString());
        return mText;
    }

    }

