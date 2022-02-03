package org.deiverbum.app.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import org.deiverbum.app.utils.Utils;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *     Esta clase recoge información valiosa sobre el día litúrgico.
 * </p>
 * @author A. Cedano
 * @version 1.0
 * @date 29/11/21
 * @since 2021.01
 */
@IgnoreExtraProperties
public class Today {
    private MetaLiturgia meta;
    //private HashMap<String,String> lh;

    public Today() {
    }


    public MetaLiturgia getMeta() {
        return meta;
    }

    public void setMeta(MetaLiturgia meta) {
        this.meta = meta;
    }
/*
    public HashMap<String,String> getLh() {
        return lh;
    }

    public void setLh(HashMap<String,String> lh) {
        this.lh = lh;
    }
*/
}




