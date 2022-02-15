package org.deiverbum.app.data;
import android.content.Context;
import org.deiverbum.app.R;
import java.io.InputStream;
import javax.inject.Inject;
import dagger.hilt.android.qualifiers.ApplicationContext;

public class ResourcesProvider {
    private final Context mContext;

    @Inject
    public ResourcesProvider(@ApplicationContext Context context) {
        mContext = context;
    }

    public String getString() {
        return mContext.getString(R.string.author_text);
    }


    public String getText() {
        int rawId = R.raw.privacy_202201;
        try {
            InputStream in_s = mContext.getResources().openRawResource(rawId);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            in_s.close();
            return new String(b);

        } catch (Exception e) {
            return String.format("Error: <br>%s", e.getMessage());
        }
    }
}
