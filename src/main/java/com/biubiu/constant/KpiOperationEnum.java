package com.biubiu.constant;

/**
 * Created by Haibiao.Zhang on 2018/12/18.
 */
public enum KpiOperationEnum {

    NOT_SUPPORT("不支持该项kpi的统计");

    KpiOperationEnum(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

}
