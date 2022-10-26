package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.BR;
import static org.deiverbum.app.utils.Constants.NBSP_SALMOS;
import static org.deiverbum.app.utils.Constants.TITLE_PSALMODY;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import androidx.room.Ignore;

import org.deiverbum.app.utils.Utils;

import java.util.Collections;
import java.util.List;

public class LHPsalmodyJoin {
    public Integer groupID;
    public int type;

    public LHPsalmodyJoin() {
    }
}