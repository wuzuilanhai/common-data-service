package com.biubiu.constant;

/**
 * Created by Haibiao.Zhang on 2018/12/17.
 */
public enum DatabaseEnum {

    TRADE("trade"), USER("user");

    DatabaseEnum(String database) {
        this.database = database;
    }

    private String database;

    public String getDatabase() {
        return database;
    }

}
