package com.biubiu.po;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Haibiao.Zhang on 2019/1/4.
 */
@Table(name = "t_kpi")
public class Kpi {

    /**
     * kpi编码
     */
    @Id
    private Integer code;

    /**
     * 数据库名称
     */
    private String database;

    /**
     * 执行sql
     */
    private String sql;

    /**
     * 指标功能描述
     */
    private String description;

    /**
     * 查询参数
     */
    @Column(name = "query_params")
    private String queryParams;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(String queryParams) {
        this.queryParams = queryParams;
    }

}
