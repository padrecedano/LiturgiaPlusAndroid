package org.deiverbum.app.model;

public class SyncStatusOLD {
    public Integer version=0;
    public String tableName="";
    public String lastUpdate="";

    @SuppressWarnings("unused")
    public SyncStatusOLD() {}
    public SyncStatusOLD(String tableName, Integer version) {
        this.tableName=tableName;
        this.version=version;
    }
    public SyncStatusOLD(String tableName, String lastUpdate) {
        this.tableName=tableName;
        this.lastUpdate=lastUpdate;
    }
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
