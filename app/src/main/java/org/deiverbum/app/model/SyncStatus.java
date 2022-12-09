package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.BR;
import static org.deiverbum.app.utils.Constants.BRS;
import static org.deiverbum.app.utils.Constants.CSS_RED_A;
import static org.deiverbum.app.utils.Constants.CSS_RED_Z;
import static org.deiverbum.app.utils.Constants.NBSP_4;

import androidx.room.Ignore;

public class SyncStatus {
    public String tableName="";
    //@Ignore
    public Integer versionDB=1;
    public String lastUpdate="";

    @SuppressWarnings("unused")
    public SyncStatus() {}


    public String getAll() {
        //return "aaa";
        return String.format("Última fecha disponible en el calendario: %s%s" +
                        "<b>%s%s%s</b> (*)" +
                        "%sÚltima sincronización realizada: %s" +
                        "<b>%s%s%s</b>"+
                "%s<small>..............%s%s(*) El calendario se sincroniza peiródicamente cuando tienes conexión a internet.</small>",
                BR,NBSP_4,
                CSS_RED_A,tableName,CSS_RED_Z,
                BRS,NBSP_4,
                CSS_RED_A,lastUpdate,CSS_RED_Z,
                BRS,BR,BR);
    }
}