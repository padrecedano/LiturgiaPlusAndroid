package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.BR;
import static org.deiverbum.app.utils.Constants.BRS;
import static org.deiverbum.app.utils.Constants.CSS_RED_A;
import static org.deiverbum.app.utils.Constants.CSS_RED_Z;
import static org.deiverbum.app.utils.Constants.NBSP_4;

public class SyncStatus {
    public String tableName="";
    public Integer versionDB=1;
    public String lastUpdate="";

    @SuppressWarnings("unused")
    public SyncStatus() {}
    public SyncStatus(String tableName, Integer versionDb, String lastUpdate) {
        this.tableName=tableName;
        this.versionDB=versionDb;
        this.lastUpdate=lastUpdate;

    }

    public SyncStatus(String tableName) {
        this.tableName=tableName;

    }

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

    /*public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }*/

}
