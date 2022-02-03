package org.deiverbum.app.repository;

import static org.deiverbum.app.utils.Constants.CALENDAR_PATH;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.deiverbum.app.R;
import org.deiverbum.app.model.Breviario;
import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.utils.FirestoreLiveData;
import org.deiverbum.app.utils.Utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by a. Cedano on 2021/11/11
 */
public class AboutRepository {
    private MutableLiveData<String> mText;

    @Inject
    public AboutRepository() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText(Context context) {
        int rawId = R.raw.about_201902;
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
}

