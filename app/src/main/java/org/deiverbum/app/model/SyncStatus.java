package org.deiverbum.app.model;

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

    /*public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }*/

}
