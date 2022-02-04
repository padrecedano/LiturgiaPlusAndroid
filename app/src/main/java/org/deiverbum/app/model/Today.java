package org.deiverbum.app.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

/**
 * <p>
 *     Esta clase recoge información valiosa sobre el día litúrgico.
 * </p>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
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

}




