package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;
import static org.deiverbum.app.utils.Utils.normalizeEnd;

import android.text.SpannableStringBuilder;

import androidx.room.Ignore;

import org.deiverbum.app.utils.Utils;

public class MisaLectura {
    //@Ignore
    public Integer liturgyFK=0;
    public Integer readingFK=0;
    public Integer theOrder=0;
    public String theme;
}
